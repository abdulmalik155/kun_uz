package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.ProfileRequestDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.enums.ProfileRole;

public class ProfileUtil {

    public static ProfileEntity toEntity(ProfileRequestDTO dto, ProfileEntity entity){
        checkRequestDTO(dto);
        entity.setName(dto.getName().trim());
        entity.setPhone(dto.getPhone().trim());
        entity.setPassword(dto.getPassword().trim());
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());
        return entity;
    }

    private static void checkRequestDTO(ProfileRequestDTO dto){
        if(dto.getSurname() != null && dto.getSurname().trim().length() < 3){
            throw new IllegalArgumentException("Invalid surname. Surname can not be empty");
        }
        if(dto.getRole() != null && dto.getRole().equals(ProfileRole.ROLE_ADMIN)){
            throw new IllegalArgumentException("Admin can not create another admin or user, only (moderator or publisher)");
        }
    }

    public static ProfileResponseDTO toDto(ProfileEntity entity){
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
