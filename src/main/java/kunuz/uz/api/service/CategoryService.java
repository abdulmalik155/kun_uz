package kunuz.uz.api.service;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.category.CategoryRequestDTO;
import kunuz.uz.api.dto.request.category.filter.CategoryFilterDTO;
import kunuz.uz.api.dto.response.category.CategoryResponseDTO;
import kunuz.uz.api.entity.CategoryEntity;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.exceptions.CategoryNotFoundException;
import kunuz.uz.api.mapper.category.CategoryShort;
import kunuz.uz.api.mapper.region.RegionShort;
import kunuz.uz.api.repository.custom.classes.CategoryCustomRepository;
import kunuz.uz.api.repository.interfaces.CategoryRepository;
import kunuz.uz.api.util.CategoryUtil;
import kunuz.uz.api.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryCustomRepository customRepository;

    @Autowired
    private CategoryUtil util;
    //5. Category
//    1. Create  (ADMIN)
//        (order_number,name_uz, name_ru, name_en)
//    2. Update by id (ADMIN)
//        (order_number,name_uz, name_ru, name_en)
//    3. Delete by id (ADMIN)
//    4. Get List (ADMIN) - order by order_number (Pagination)
//        (id,order_number,name_uz, name_ru, name_en,visible,created_date)
//    5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
//        (id,order_number,name) (name ga tegishli name_... dagi qiymat qo'yiladi.)
    public CategoryResponseDTO create(@Valid CategoryRequestDTO dto) {
        CategoryEntity entityToSave = util.toEntity(dto,new CategoryEntity());
        entityToSave.setVisible(true);
        entityToSave.setOrderNumber(dto.getOrderNumber());
        CategoryResponseDTO responseDTO = util.toResponseDto(repository.save(entityToSave));
        return responseDTO;
    }

    public CategoryResponseDTO update(Integer id, CategoryRequestDTO dto){
        CategoryEntity existingEntity = getEntityOrThrow(id);
        existingEntity = util.toEntity(dto,existingEntity);
        CategoryEntity updatedEntity = repository.save(existingEntity);
        CategoryResponseDTO responseDTO = util.toResponseDto(updatedEntity);
        return responseDTO;
    }

    public void delete(Integer id){
        CategoryEntity entityToDelete = getEntityOrThrow(id);
        entityToDelete.setVisible(false);

    }

    public Page<CategoryResponseDTO> categoryList(int page, int size, CategoryFilterDTO dto){
        PaginationUtil.check(page,size);
        page--;
        Page<CategoryEntity> entities = customRepository.findAll(page,size, dto);
        return new PageImpl<>(util.toResponseDtoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    public Page<CategoryShort> getByLanguage(int page, int size, Language language) {
        PaginationUtil.check(page, size);
        page--;
        if (language == null ) throw new IllegalArgumentException("Language of name of category can not be empty");

        Page<Object[]> entities = customRepository.getByLanguage(page,size,language);

        return new PageImpl<>(util
                .shortToResponseDtoList(
                        entities.getContent()
                ),
                entities.getPageable(),
                entities.getTotalElements());
    }
    private CategoryEntity getEntityOrThrow(Integer id){
        return repository.findById(id).orElseThrow(()->new CategoryNotFoundException(String.format("Category with %s id not found", id)));
    }

}
