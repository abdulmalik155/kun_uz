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
        // check
        Optional<ProfileEntity> existOptional = profileRepository.findByUsername(dto.getUsername());
        if (existOptional.isPresent()) {
            ProfileEntity existsProfile = existOptional.get();
            if (existsProfile.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                profileRoleService.deleteById(existsProfile.getId());
                profileRepository.deleteById(existsProfile.getId()); // delete
            } else {
                throw new IllegalArgumentException("Username already exists");
            }
        }
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setToAccount(dto.getUsername());
        messageDTO.setSubject("Ro'yhatdan o'tish");
        List<EmailHistoryResponseDTO> existingMails = emailHistoryService.getByEmail(messageDTO.getToAccount());
        if (existingMails.size() >= 4) {
            EmailHistoryResponseDTO last = existingMails.getLast();
            List<EmailHistoryResponseDTO> extracted = existingMails.subList(existingMails.indexOf(last) - 3, existingMails.indexOf(last));
            if (extracted
                    .getFirst()
                    .getCreatedDate()
                    .plusMinutes(1)
                    .isAfter(
                            LocalDateTime.now()
                    )
            ) {
                throw new IllegalArgumentException("Verification code generation attempts exceeded the limit");
            }
        }
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
        // send sms to amail or phone
        int verificationCode = randomClassConfig.random().nextInt(100000, 999999);
        emailSendingService.sendEmailHtmlFormatted(messageDTO, verificationCode);
        //email history
        emailHistoryService.create(messageDTO, verificationCode);

        //confirm
        // response
        return "Tastiqlash kodi ketdi mazgi qara.";
    }

    public void confirm(ConfirmationDTO dto) {
        List<EmailHistoryResponseDTO> list = emailHistoryService.getByEmail(dto.getEmail());
        EmailHistoryResponseDTO last = list.getLast();
        if (last.getCreatedDate().plusMinutes(1).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Code expired");
        }
        if (last.getMessage().equals(dto.getMessage()) && last.getEmail().equals(dto.getEmail())) {
            profileRepository.updateStatusByUsername(dto.getEmail(), ProfileStatus.ACTIVE);
            emailHistoryService.deleteByEmail(dto.getEmail());
        }else{
            throw new IllegalArgumentException("o'xshamadi");
        }
    }

    public ProfileResponseDTO login(AuthorizationDTO dto) {
        Optional<ProfileEntity> profileOptional = profileRepository.findByUsername(dto.getUsername());
        if (profileOptional.isEmpty()) {
            throw new IllegalArgumentException("Username or password wrong");
        }
        ProfileEntity entity = profileOptional.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            throw new IllegalArgumentException("Username or password wrong");
        }
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new IllegalArgumentException("User in wrong status");
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
