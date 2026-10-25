package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.ProfileRequestDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.repository.interfaces.ProfileRepository;
import kunuz.uz.api.util.ProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repository;

    public ProfileResponseDTO create(ProfileRequestDTO dto) {
//        1. Create profile (ADMIN) (can create MODERATOR,PUBLISHER))
//                 (name,surname,email,phone,password,status,role)
        ProfileEntity entity = ProfileUtil.toEntity(dto, new ProfileEntity());
                repository.save(entity);
        return ProfileUtil.toDto(entity);
    }


}
