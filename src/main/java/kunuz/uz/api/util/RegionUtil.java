package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.BaseRegionDTO;
import kunuz.uz.api.dto.request.RegionRequestDTO;
import kunuz.uz.api.dto.response.RegionResponseDTO;
import kunuz.uz.api.entity.RegionEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class RegionUtil implements BaseEntityUtil<BaseRegionDTO, RegionResponseDTO, RegionEntity> {

    @Override
    public RegionEntity toEntity(BaseRegionDTO dto, RegionEntity entity) {
        if(dto.getClass().equals(RegionRequestDTO.class)){
            entity.setOrderNumber(((RegionRequestDTO) dto).getOrderNumber());
        }
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        if (entity.getId() == null) {
            entity.setVisible(true);
        }
        return entity;
    }

    @Override
    public RegionResponseDTO toDto(RegionEntity entity) {
        RegionResponseDTO dto = new RegionResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
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
