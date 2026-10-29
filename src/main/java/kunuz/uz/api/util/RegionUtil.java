package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.RegionRequestDTO;
import kunuz.uz.api.dto.response.RegionResponseDTO;
import kunuz.uz.api.entity.RegionEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class RegionUtil implements BaseEntityUtil<RegionRequestDTO, RegionResponseDTO, RegionEntity> {

    @Override
    public RegionEntity toEntity(RegionRequestDTO dto, RegionEntity entity) {
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        return entity;
    }

    @Override
    public RegionResponseDTO toDto(RegionEntity entity) {
        RegionResponseDTO dto = new RegionResponseDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setKey(entity.getKey());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    @Override
    public List<RegionResponseDTO> toDtoList(Iterable<RegionEntity> iterable) {
        List<RegionResponseDTO> list = new LinkedList<>();
        for (RegionEntity entity : iterable) {
            list.add(toDto(entity));
        }
        return list;
    }

}
