package kunuz.uz.api.util;

import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.entity.ProfileEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ProfileUtil {

    public ProfileResponseDTO toDto(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
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
