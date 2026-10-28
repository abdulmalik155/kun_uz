package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.category.BaseCategoryDTO;
import kunuz.uz.api.dto.response.category.CategoryResponseDTO;
import kunuz.uz.api.entity.CategoryEntity;
import kunuz.uz.api.mapper.category.CategoryShort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryUtil implements BaseEntityUtil<BaseCategoryDTO, CategoryResponseDTO, CategoryEntity>, MappingFromArray<CategoryShort> {

    @Override
    public CategoryEntity toEntity(BaseCategoryDTO dto, CategoryEntity categoryEntity) {

        return null;
    }

    @Override
    public CategoryResponseDTO toResponseDto(CategoryEntity categoryEntity) {
        return null;
    }

    @Override
    public List<CategoryResponseDTO> toResponseDtoList(Iterable<CategoryEntity> iterable) {
        return List.of();
    }

    @Override
    public List<CategoryShort> shortToResponseDtoList(List<Object[]> content) {
        return List.of();
    }
}
