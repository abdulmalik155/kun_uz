package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.ArticleEntity;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.mapper.article.ArticleShortInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, UUID>, PagingAndSortingRepository<ArticleEntity, UUID> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE ArticleEntity AS a SET a.visible = FALSE WHERE a.id = ?1")
    void updateVisibleById(UUID id);

    @Query(value = "SELECT a " +
            " FROM ArticleEntity AS a " +
            " WHERE a.moderatorId = :moderator_id AND " +
            " a.regionId = :region_id AND " +
            " lower(a.title) = lower(:title_new) AND " +
            " lower(a.description) = lower(:description_new) AND " +
            " lower(a.content) = lower(:content_new) AND " +
            " a.visible = TRUE AND " +
            " cast(a.createdDate AS DATE) = CURRENT_DATE")
    Optional<ArticleEntity> findDuplicate(@Param("moderator_id") Long moderatorId,
                                          @Param("region_id") Integer regionId,
                                          @Param("title_new") String title,
                                          @Param("description_new") String description,
                                          @Param("content_new") String content);


    @Query(value = "SELECT a " +
            " FROM ArticleEntity AS a " +
            " WHERE a.id = ?1 " +
            " AND a.visible = TRUE")
    Optional<ArticleEntity> findByIdVisibleTrue(UUID id);

    @Query(value = "SELECT a.id AS id, " +
            " a.title AS title, " +
            " a.description AS description, " +
            " a.imageId AS imageId, " +
            " a.publishedDate AS publishedDate " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN a.sectionList AS s " +
            " WHERE s.sectionId = ?1 " +
            " AND a.visible = TRUE " +
            " AND a.status = 'PUBLISHED'")
    Page<ArticleShortInfo> findLastNBySectionId(Integer sectionId, PageRequest request);


    @Query(value = "SELECT a.id AS id, " +
            " a.title AS title, " +
            " a.description AS description, " +
            " a.imageId AS imageId, " +
            " a.publishedDate AS publishedDate " +
            " FROM ArticleEntity AS a " +
            " WHERE a.status = 'PUBLISHED' " +
            " AND a.visible = TRUE " +
            " AND a.id NOT IN ?1 ")
    Page<ArticleShortInfo> findLast12Except(List<UUID> articleIds, PageRequest request);

    @Query(value = "SELECT a.id AS id, " +
            " a.title AS title, " +
            " a.description AS description, " +
            " a.imageId AS imageId, " +
            " a.publishedDate AS publishedDate " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN a.categoryList AS c " +
            " WHERE c.categoryId = ?1 " +
            " AND a.visible = TRUE " +
            " AND a.status = 'PUBLISHED'")
    Page<ArticleShortInfo> findLastNByCategoryId(Integer categoryId, PageRequest request);

    @Query(value = "SELECT a " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN a.region AS r " +
            " WHERE r.id = ?1 " +
            " AND a.visible = TRUE " +
            " AND a.status = 'PUBLISHED'")
    Page<ArticleEntity> findLastNByRegionId(Integer regionId, PageRequest request);

    @Query(value = "SELECT a " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN a.tagList AS tag " +
            " WHERE a.visible = TRUE " +
            " AND tag.name = ?1")
    Page<ArticleEntity> findLastNByTag(String name, PageRequest request);

//    @Query(value = "SELECT a " +
//            " FROM ArticleEntity AS a " +
//            " INNER JOIN a.categoryList AS category " +
//            " INNER JOIN a.sectionList AS section " +
//            "  " +
//            " WHERE a.id = :articleId AND a.visible = TRUE")
//    Page<ArticleEntity> findLastNByIdAndLang(@Param(value = "articleId") UUID articleId,
//                                             @Param(value = "lang") String language,
//                                             PageRequest request);
}
