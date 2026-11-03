package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.ArticleCategoryEntity;
import kunuz.uz.api.entity.CategoryEntity;
import kunuz.uz.api.mapper.category.CategoryShort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity, Integer> {

    @Query(value = "SELECT c " +
            " FROM ArticleCategoryEntity AS ac " +
            " INNER JOIN ac.category AS c " +
            " WHERE ac.articleId = ?1 ")
    List<CategoryEntity> findAllByArticleId(UUID articleId);

    @Transactional
    @Modifying
    @Query(value = "DELETE " +
            " FROM ArticleCategoryEntity AS ac " +
            " WHERE ac.articleId = ?1 " +
            " AND ac.categoryId = ?2 ")
    void deleteByArticleAndCategoryIds(UUID articleId, Integer categoryId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ArticleCategoryEntity AS ac WHERE ac.articleId = ?1 ")
    void deleteByArticleId(UUID articleId);

    @Query(value = "SELECT c.id AS id, " +
            " c.key AS key, " +
            " c.nameUz AS name " +
            " FROM ArticleCategoryEntity AS ac " +
            " INNER JOIN ac.category AS c " +
            " WHERE ac.articleId = ?1")
    List<CategoryShort> getAllByArticleIdShort(UUID articleId);

    @Query(value = "SELECT category.id AS id, " +
            " category.key AS key, " +
            " (CASE :lang " +
            "      WHEN 'UZ' THEN category.nameUz " +
            "      WHEN 'RU' THEN category.nameRu " +
            "      WHEN 'EN' THEN category.nameEn " +
            "           ELSE category.nameUz " +
            "      END " +
            ") AS name " +
            " FROM ArticleCategoryEntity AS ac " +
            " INNER JOIN ac.category AS category" +
            " WHERE ac.articleId = :article_id ")
    List<CategoryShort> findAllByArticleIdAndLang(@Param(value = "article_id") UUID articleId,
                                                   @Param(value = "lang") String language);

}
