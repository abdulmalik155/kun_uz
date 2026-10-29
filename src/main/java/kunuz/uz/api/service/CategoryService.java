package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.CategoryRequestDTO;
import kunuz.uz.api.dto.response.CategoryResponseDTO;
import kunuz.uz.api.entity.CategoryEntity;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.exceptions.category.CategoryExistsException;
import kunuz.uz.api.exceptions.category.CategoryNotFoundException;
import kunuz.uz.api.mapper.category.CategoryShort;
import kunuz.uz.api.repository.interfaces.CategoryRepository;
import kunuz.uz.api.util.CategoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryUtil util;

    public CategoryResponseDTO create(CategoryRequestDTO newDto) {
        checkKeyAndOrder(newDto);
        CategoryEntity newEntity = util.toEntity(newDto, new CategoryEntity());
        newEntity.setVisible(true);
        CategoryResponseDTO responseDTO = util.toDto(repository.save(newEntity));
        return responseDTO;
    }

    public CategoryResponseDTO update(Integer id, CategoryRequestDTO newDto) {
        checkKeyAndOrder(id, newDto);
        CategoryEntity existingEntity = util.toEntity(newDto, getEntityOrThrow(id));
        CategoryEntity updatedEntity = repository.save(existingEntity);
        CategoryResponseDTO responseDTO = util.toDto(updatedEntity);
        return responseDTO;
    }

    public void delete(Integer id) {
        repository.updateVisibleById(id);
    }

    public List<CategoryResponseDTO> all() {
        return util.toDtoList(repository.allSortedByOrder());
    }

    public List<CategoryShort> allByLanguage(Language language) {
        if (language == null) {
            throw new IllegalArgumentException("Language of name of category can not be empty");
        }
        List<CategoryShort> entities = repository.allByLanguage(language.toString());
        return entities;
    }

    private CategoryEntity getEntityOrThrow(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new CategoryNotFoundException(String.format("Category with %s id not found", id)));
    }

    private void checkKeyAndOrder(CategoryRequestDTO newDto) {
        Optional<CategoryEntity> optionalByKey = repository.findByKey(newDto.getKey());
        if (optionalByKey.isPresent()) {
            throw new CategoryExistsException("Category exists");
        }
        Optional<CategoryEntity> optionalByOrder = repository.findByOrderNumber(newDto.getOrderNumber());
        if (optionalByOrder.isPresent()) {
            throw new IllegalArgumentException("This position is taken");
        }
    }

    private void checkKeyAndOrder(Integer id, CategoryRequestDTO newDto) {
        Optional<CategoryEntity> optionalByKey = repository.findByKey(newDto.getKey());
        if (optionalByKey.isPresent() && !optionalByKey.get().getId().equals(id)) {
            throw new CategoryExistsException("Category exists");
        }
        Optional<CategoryEntity> optionalByOrder = repository.findByOrderNumber(newDto.getOrderNumber());
        if (optionalByOrder.isPresent() && !optionalByOrder.get().getId().equals(id)) {
            throw new IllegalArgumentException("This position is taken");
        }
    }

}
