package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.ArticleCategoryEntity;
import kunuz.uz.api.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity, Integer> {

    @Query(value = "SELECT ac.category FROM ArticleCategoryEntity AS ac WHERE ac.articleId = ?1 ")
    List<CategoryEntity> findAllByArticleId(UUID articleId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ArticleCategoryEntity AS ac WHERE ac.articleId = ?1 AND ac.categoryId = ?2 ")
    void deleteByArticleAndCategoryIds(UUID articleId, Integer categoryId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ArticleCategoryEntity AS ac WHERE ac.articleId = ?1 ")
    void deleteByArticleId(UUID articleId);
}
