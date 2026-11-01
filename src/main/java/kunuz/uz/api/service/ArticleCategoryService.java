package kunuz.uz.api.service;

import kunuz.uz.api.dto.response.CategoryResponseDTO;
import kunuz.uz.api.entity.ArticleCategoryEntity;
import kunuz.uz.api.entity.CategoryEntity;
import kunuz.uz.api.repository.interfaces.ArticleCategoryRepository;
import kunuz.uz.api.util.CategoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleCategoryService {

    @Autowired
    private ArticleCategoryRepository repository;

    @Autowired
    private CategoryUtil util;

    public void create(UUID articleId, List<Integer> categoryList){
        for (Integer categoryId : categoryList) {
            ArticleCategoryEntity entity = new ArticleCategoryEntity();
            entity.setArticleId(articleId);
            entity.setCategoryId(categoryId);
            repository.save(entity);
        }
    }

    public void merge(UUID articleId, List<Integer> newList){
        List<CategoryEntity> old = getAllByArticleId(articleId);
        List<Integer> oldList = new LinkedList<>();
        for (CategoryEntity entity : old) {
            oldList.add(entity.getId());
        }
        for (Integer categoryId : newList) {
            if(!oldList.contains(categoryId)){
                create(articleId, categoryId);
            }
        }
        for (Integer categoryId : oldList) {
            if(!newList.contains(categoryId)){
                deleteByArticleAndCategoryIds(articleId, categoryId);
            }
        }
    }

    private void deleteByArticleAndCategoryIds(UUID articleId, Integer categoryId) {
        repository.deleteByArticleAndCategoryIds(articleId,categoryId);
    }

    private void deleteByArticleId(UUID articleId){
        repository.deleteByArticleId(articleId);
    }

    public List<CategoryEntity> getAllByArticleId(UUID articleId){
        return repository.findAllByArticleId(articleId);
    }

    public List<CategoryResponseDTO> getAllDTOByArticleId(UUID articleId){
        List<CategoryResponseDTO> response = new LinkedList<>();
        for (CategoryEntity entity : repository.findAllByArticleId(articleId)) {
            response.add(util.toDto(entity));
        }
        return response;
    }

    public void create(UUID articleId, Integer categoryId){
            ArticleCategoryEntity entity = new ArticleCategoryEntity();
            entity.setArticleId(articleId);
            entity.setCategoryId(categoryId);
            repository.save(entity);
    }

}
