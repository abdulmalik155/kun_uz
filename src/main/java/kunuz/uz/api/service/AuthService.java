package kunuz.uz.api.service;


import jakarta.validation.Valid;
import kunuz.uz.api.config.RandomClassConfig;
import kunuz.uz.api.dto.AuthorizationDTO;
import kunuz.uz.api.dto.ConfirmationDTO;
import kunuz.uz.api.dto.request.MessageDTO;
import kunuz.uz.api.dto.request.RegistrationDTO;
import kunuz.uz.api.dto.response.EmailHistoryResponseDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.entity.EmailHistoryEntity;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.enums.ProfileStatus;
import kunuz.uz.api.exceptions.AppBadException;
import kunuz.uz.api.exceptions.profile.ProfileExistsException;
import kunuz.uz.api.repository.interfaces.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProfileRoleService profileRoleService;

    @Autowired
    private EmailSendingService emailSendingService;

    @Autowired
    private RandomClassConfig randomClassConfig;

    @Autowired
    private EmailHistoryService emailHistoryService;

    public String registration(RegistrationDTO dto) {
        // check whether profile exists and it's status is ACTIVE
        profileExistCheck(dto);
        // check whether it made too many code generation email requests
        messageAttemptsCheck(dto.getUsername());
        // create profile
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        profile.setVisible(true);
        profile.setStatus(ProfileStatus.NOT_ACTIVE);
        profileRepository.save(profile);
        // create profile roles
        profileRoleService.create(profile.getId(), ProfileRole.ROLE_USER);
        //create message dto to send verification email code
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setToAccount(dto.getUsername());
        messageDTO.setSubject("Ro'yhatdan o'tish");

        int verificationCode = randomClassConfig.random().nextInt(100000, 999999);

        // send sms to amail or phone
        emailSendingService.sendEmailHtmlFormatted(messageDTO, verificationCode);
        //email history
        emailHistoryService.create(messageDTO, verificationCode);

        //confirm
        // response
        return "Tastiqlash kodi ketdi mazgi qara.";
    }

    private void profileExistCheck(RegistrationDTO dto) {
        Optional<ProfileEntity> existOptional = profileRepository.findByUsername(dto.getUsername());
        if (existOptional.isPresent()) {
            ProfileEntity existsProfile = existOptional.get();
            if (existsProfile.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                profileRoleService.deleteById(existsProfile.getId());
                profileRepository.deleteById(existsProfile.getId()); // delete
            } else {
                throw new ProfileExistsException("Username already exists");
            }
        }
    }

    private void messageAttemptsCheck(String username) {
        List<EmailHistoryResponseDTO> existingMails = emailHistoryService.getByEmail(username);
        if (existingMails.size() >= 4) {
            EmailHistoryResponseDTO last = existingMails.getLast();
            List<EmailHistoryResponseDTO> lastFour =
                    existingMails.
                            subList(existingMails.indexOf(last) - 3,
                                    existingMails.indexOf(last));
            EmailHistoryResponseDTO first = lastFour.getFirst();
            if (first.getCreatedDate()
                    .plusMinutes(1)
                    .isAfter(LocalDateTime.now())
            ) {
                throw new AppBadException("Verification code generation attempts exceeded the limit");
            }
        }
    }

    public void confirm(ConfirmationDTO dto) {
        List<EmailHistoryResponseDTO> list = emailHistoryService.getByEmail(dto.getEmail());
        //get latest email sent to user
        EmailHistoryResponseDTO last = list.getLast();
        //check if code matches with ours
        if (!last.getMessage().equals(dto.getMessage())) {
            throw new AppBadException("Wrong code");
        }
        if (last.getCreatedDate()
                .plusMinutes(1)
                .isBefore(LocalDateTime.now())) {
            throw new AppBadException("Code expired");
        }

        profileRepository.updateStatusByUsername(dto.getEmail(), ProfileStatus.ACTIVE);
        emailHistoryService.deleteByEmail(dto.getEmail());
    }

    public ProfileResponseDTO login(AuthorizationDTO dto) {
        Optional<ProfileEntity> profileOptional = profileRepository.findByUsername(dto.getUsername());
        if (profileOptional.isEmpty()) {
            throw new AppBadException("Username or password wrong");
        }
        ProfileEntity entity = profileOptional.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            throw new AppBadException("Username or password wrong");
        }
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException("User in wrong status");
        }
        ProfileResponseDTO response = new ProfileResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setUsername(entity.getUsername());
        response.setRoleList(profileRoleService.getAllByProfileId(entity.getId()));
        return response;
    }

}
