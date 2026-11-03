package kunuz.uz.api.service;


import ch.qos.logback.core.testUtil.RandomUtil;
import kunuz.uz.api.dto.AuthorizationDTO;
import kunuz.uz.api.dto.request.MessageDTO;
import kunuz.uz.api.dto.request.RegistrationDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.enums.ProfileStatus;
import kunuz.uz.api.repository.interfaces.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private ProfileRoleService roleService;

    @Autowired
    private EmailSendingService emailService;


    public String registration(RegistrationDTO dto) {
        // check
        Optional<ProfileEntity> existOptional = profileRepository.findByUsername(dto.getUsername());
        if (existOptional.isPresent()) {
//            throw new AppBadException("Username already exists");

            ProfileEntity existsProfile = existOptional.get();
            if (existsProfile.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                roleService.deleteById(existsProfile.getId());
                profileRepository.deleteById(existsProfile.getId()); // delete
            } else {
                throw new IllegalArgumentException("Username already exists");
            }
        }
        // create profile
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setPassword(encoder.encode(dto.getPassword()));
        profile.setVisible(true);
        profile.setStatus(ProfileStatus.NOT_ACTIVE);
        profileRepository.save(profile);
        // create profile roles
        roleService.create(profile.getId(), ProfileRole.ROLE_USER);
        // send sms to amail or phone

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setToAccount(dto.getUsername());
        messageDTO.setSubject("Ro'yhatdan o'tish");
        Random rand = new Random();
        messageDTO.setText(String.valueOf(rand.nextInt(10000,99999))); //
        emailService.sendSimpleMessage(messageDTO);

        // response
        return "Tastiqlash kodi ketdi mazgi qara.";
    }

    public ProfileResponseDTO login(AuthorizationDTO dto) {
        Optional<ProfileEntity> profileOptional = profileRepository.findByUsername(dto.getUsername());
        if (profileOptional.isEmpty()) {
            throw new IllegalArgumentException("Username or password wrong");
        }
        ProfileEntity entity = profileOptional.get();
        if (!encoder.matches(dto.getPassword(), entity.getPassword())) {
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
        response.setRoleList(roleService.getAllByProfileId(entity.getId()));
        return response;
    }


}
