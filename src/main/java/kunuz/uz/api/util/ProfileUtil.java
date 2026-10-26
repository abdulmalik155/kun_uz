package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.profile.ProfileRequestDTO;
import kunuz.uz.api.dto.request.profile.BaseProfileDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.enums.ProfileRole;

import java.util.LinkedList;
import java.util.List;

public class ProfileUtil {

    public static ProfileEntity toEntity(ProfileRequestDTO dto, ProfileEntity entity) {
        entity.setName(dto.getName().trim());
        if (dto.getSurname() != null && !dto.getSurname().isBlank()) {
            entity.setSurname(dto.getSurname());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        entity.setPhone(dto.getPhone().trim());
        entity.setPassword(dto.getPassword().trim());
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());
        return entity;
    }

    public static ProfileEntity toEntityDetail(BaseProfileDTO dto, ProfileEntity entity) {
        entity.setName(dto.getName().trim());
        if (dto.getSurname() != null && !dto.getSurname().isBlank()) {
            entity.setSurname(dto.getSurname());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        entity.setPhone(dto.getPhone().trim());
        entity.setPassword(dto.getPassword().trim());
        return entity;
    }


    public static void check(ProfileRequestDTO dto) {
        /*if (dto.getSurname() != null && dto.getSurname().trim().length() < 3) {
            throw new IllegalArgumentException("Invalid surname. Surname can not be empty");
        }*/
        if (dto.getRole() != null && (dto.getRole().equals(ProfileRole.ROLE_USER) || dto.getRole().equals(ProfileRole.ROLE_ADMIN))) {
            throw new IllegalArgumentException("Admin can not work with another admin or user, only (moderator or publisher)");
        }
    }

    public static ProfileResponseDTO toDto(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public static List<ProfileResponseDTO> toList(Iterable<ProfileEntity> iterable) {
        List<ProfileResponseDTO> list = new LinkedList<>();
        for (ProfileEntity entity : iterable) {
            list.add(toDto(entity));
        }
        return list;
    }

}
