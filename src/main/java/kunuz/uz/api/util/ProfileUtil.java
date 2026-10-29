package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.ProfileRequestDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.enums.ProfileStatus;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ProfileUtil implements BaseEntityUtil<ProfileRequestDTO, ProfileResponseDTO, ProfileEntity> {

    public ProfileEntity toEntity(ProfileRequestDTO dto, ProfileEntity entity) {
        entity.setName(dto.getName().trim());
        if (dto.getSurname() != null && !dto.getSurname().isBlank()) {
            entity.setSurname(dto.getSurname());
        }
        entity.setPassword(dto.getPassword().trim());
        if (entity.getId() == null) {
            entity.setStatus(ProfileStatus.ACTIVE);
            entity.setVisible(true);
        }
        return entity;
    }

    public void check(ProfileRequestDTO dto) {
        /*if (dto.getSurname() != null && dto.getSurname().trim().length() < 3) {
            throw new IllegalArgumentException("Invalid surname. Surname can not be empty");
        }*/
//        if (dto.getRole() != null && (dto.getRole().equals(ProfileRole.ROLE_USER) || dto.getRole().equals(ProfileRole.ROLE_ADMIN))) {
//            throw new IllegalArgumentException("Admin can not do operations on another admin or user, only (moderator or publisher)");
//        }
        //TODO Check that admin can only create moderator and publisher and convert from user to those 2 but not create user
    }

    public ProfileResponseDTO toDto(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
//        dto.setRole(entity.getRole());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<ProfileResponseDTO> toDtoList(Iterable<ProfileEntity> iterable) {
        List<ProfileResponseDTO> list = new LinkedList<>();
        for (ProfileEntity entity : iterable) {
            list.add(toDto(entity));
        }
        return list;
    }

}
