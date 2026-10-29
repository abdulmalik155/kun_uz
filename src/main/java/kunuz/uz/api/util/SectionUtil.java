package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.SectionRequestDTO;
import kunuz.uz.api.dto.response.SectionResponseDTO;
import kunuz.uz.api.entity.SectionEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class SectionUtil implements BaseEntityUtil<SectionRequestDTO, SectionResponseDTO, SectionEntity> {

    public SectionEntity toEntity(SectionRequestDTO dto, SectionEntity entity) {
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        return entity;
    }

    public SectionResponseDTO toDto(SectionEntity entity) {
        SectionResponseDTO dto = new SectionResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public List<SectionResponseDTO> toDtoList(Iterable<SectionEntity> iterable) {
        List<SectionResponseDTO> list = new LinkedList<>();
        for (SectionEntity articleTypeEntity : iterable) {
            list.add(toDto(articleTypeEntity));
        }
        return list;
    }

}
