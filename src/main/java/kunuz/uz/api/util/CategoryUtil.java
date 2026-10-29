package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.CategoryRequestDTO;
import kunuz.uz.api.dto.response.CategoryResponseDTO;
import kunuz.uz.api.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class CategoryUtil implements BaseEntityUtil<CategoryRequestDTO, CategoryResponseDTO, CategoryEntity> {

    @Override
    public CategoryEntity toEntity(CategoryRequestDTO dto, CategoryEntity entity) {
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setKey(dto.getKey());
        return entity;
    }

    @Override
    public CategoryResponseDTO toDto(CategoryEntity entity) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
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
    public List<CategoryResponseDTO> toDtoList(Iterable<CategoryEntity> iterable) {
        List<CategoryResponseDTO> list = new LinkedList<>();
        for (CategoryEntity categoryEntity : iterable) {
            list.add(toDto(categoryEntity));
        }
        return list;
    }

}
