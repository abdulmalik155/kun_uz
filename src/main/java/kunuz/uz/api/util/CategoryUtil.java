package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.category.BaseCategoryDTO;
import kunuz.uz.api.dto.response.category.CategoryResponseDTO;
import kunuz.uz.api.entity.CategoryEntity;
import kunuz.uz.api.mapper.category.CategoryShort;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class CategoryUtil implements BaseEntityUtil<BaseCategoryDTO, CategoryResponseDTO, CategoryEntity>, MappingFromArray<CategoryShort> {

    @Override
    public CategoryEntity toEntity(BaseCategoryDTO dto, CategoryEntity entity) {
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        return entity;
    }

    @Override
    public CategoryResponseDTO toResponseDto(CategoryEntity entity) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    @Override
    public List<CategoryResponseDTO> toResponseDtoList(Iterable<CategoryEntity> iterable) {
        List<CategoryResponseDTO> list = new LinkedList<>();
        for (CategoryEntity categoryEntity : iterable) {
            list.add(toResponseDto(categoryEntity));
        }
        return list;
    }

    @Override
    public List<CategoryShort> shortToResponseDtoList(List<Object[]> content) {
        List<CategoryShort> list = new LinkedList<>();
        for (Object[] region : content) {
            CategoryShort dto = new CategoryShort();
            dto.setId((Integer) region[0]);
            dto.setName((String) region[1]);
            list.add(dto);
        }
        return list;
    }
}
