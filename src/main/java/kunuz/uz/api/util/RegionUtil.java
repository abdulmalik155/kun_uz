package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.region.BaseRegionDTO;
import kunuz.uz.api.dto.request.region.RegionRequestDTO;
import kunuz.uz.api.dto.response.region.RegionResponseDTO;
import kunuz.uz.api.entity.RegionEntity;
import kunuz.uz.api.mapper.region.RegionShort;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class RegionUtil implements EntityUpdate<BaseRegionDTO, RegionRequestDTO, RegionResponseDTO, RegionEntity>, MappingFromArray<RegionShort> {

    @Override
    public RegionEntity toEntity(RegionRequestDTO dto, RegionEntity entity) {
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        if (entity.getId() == null) {
            entity.setVisible(true);
        }
        return entity;
    }

    @Override
    public RegionEntity toEntityForUpdate(BaseRegionDTO dto, RegionEntity entity) {
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        return entity;
    }

    @Override
    public RegionResponseDTO toResponseDto(RegionEntity entity) {
        RegionResponseDTO dto = new RegionResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    @Override
    public List<RegionResponseDTO> toResponseDtoList(Iterable<RegionEntity> iterable) {
        List<RegionResponseDTO> list = new LinkedList<>();
        for (RegionEntity entity : iterable) {
            list.add(toResponseDto(entity));
        }
        return list;
    }

    @Override
    public List<RegionShort> shortToResponseDtoList(List<Object[]> content) {
        List<RegionShort> list = new LinkedList<>();
        for (Object[] region : content) {
            RegionShort dto = new RegionShort();
            dto.setId((Integer) region[0]);
            dto.setName((String) region[1]);
            list.add(dto);
        }
        return list;
    }
}
