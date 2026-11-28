package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.ArticleEntity;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.mapper.article.ArticleShortMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
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
            " a.description AS description," +
            " a.imageId AS imageId, " +
            " a.publishedDate AS publishedDate " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN a.sectionList AS s " +
            " WHERE s.sectionId = :sectionId " +
            " AND a.language = :language " +
            " AND a.visible = TRUE " +
            " AND a.status = 'PUBLISHED'")
    Page<ArticleShortMapper> findLastNBySectionId(@Param("sectionId") Integer sectionId,
                                                  @Param("language") Language language,
                                                  Pageable request);

    @Query(value = """
            SELECT a.id AS id,
            a.title AS title,
            a.description AS description,
            a.imageId AS imageId,
            a.publishedDate AS publishedDate
            FROM ArticleEntity AS a
            WHERE a.status = 'PUBLISHED'
            AND a.visible = TRUE
            AND a.id NOT IN ?1
            AND a.language = :language""")
    Page<ArticleShortMapper> findLast12Except(@Param("articleIds") List<UUID> articleIds,
                                              @Param("language") Language language,
                                              Pageable request);

    @Query(value = "SELECT a.id AS id, " +
            " a.title AS title, " +
            " a.description AS description," +
            " a.imageId AS imageId, " +
            " a.publishedDate AS publishedDate " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN a.categoryList AS c " +
            " WHERE c.categoryId = :categoryId " +
            " AND a.language = :language " +
            " AND a.visible = TRUE " +
            " AND a.status = 'PUBLISHED'")
    Page<ArticleShortMapper> findLastNByCategoryId(@Param("categoryId") Integer categoryId,
                                                   @Param("language") Language language,
                                                   Pageable request);

    @Query(value = "SELECT a " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN a.region AS r " +
            " WHERE r.id = :regionId " +
            " AND a.language = :language " +
            " AND a.visible = TRUE " +
            " AND a.status = 'PUBLISHED'")
    Page<ArticleEntity> findLastNByRegionId(@Param("regionId") Integer regionId,
                                            @Param("language") Language language, Pageable request);

    @Query(value = "SELECT a.id AS id, " +
            " a.title AS title, " +
            " a.description AS description," +
            " a.imageId AS imageId, " +
            " a.publishedDate AS publishedDate " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN ArticleTagEntity AS articleTag ON articleTag.articleId = a.id " +
            " INNER JOIN TagEntity AS tag ON articleTag.tagId = tag.id " +
            " WHERE a.visible = TRUE " +
            " AND a.language = :language " +
            " AND tag.name = :tagName ")
    Page<ArticleShortMapper> findLastNByTagName(@Param("tagName") String tagName,
                                                @Param("language") Language language, Pageable request);

    ArticleEntity findByIdAndModeratorId(UUID articleId, Long moderatorId);

    @Query(value = "SELECT a.id AS id, " +
            " a.title AS title, " +
            " a.description AS description," +
            " a.imageId AS imageId, " +
            " a.publishedDate AS publishedDate " +
            " FROM ArticleEntity AS a " +
            " INNER JOIN a.sectionList AS s " +
            " WHERE a.status = 'PUBLISHED' " +
            " AND a.visible = TRUE " +
            " AND a.id <> :articleId " +
            " AND a.language = :language " +
            " AND s.sectionId = :sectionId ")
    Page<ArticleShortMapper> findLast4BySectionIdExcept(@Param("sectionId") Integer sectionId,
                                                        @Param("articleId") UUID articleId,
                                                        @Param("language") Language language,
                                                        PageRequest request);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE ArticleEntity AS a
            SET a.viewCount = COALESCE(a.viewCount,0) + 1
            WHERE a.id = ?1
            """)
    int increaseViewCount(UUID articleId);


    @Modifying
    @Transactional
    @Query(value = """
            UPDATE ArticleEntity AS a
            SET a.sharedCount = COALESCE(a.sharedCount,0) + 1
            WHERE a.id = ?1
            """)
    int increaseShareCount(UUID articleId);



//    @Query(value = "SELECT a " +
//            " FROM ArticleEntity AS a " +
//            " INNER JOIN a.categoryList AS category " +
//            " INNER JOIN a.sectionList AS section " +
//            "  " +
//            " WHERE a.id = :articleId AND a.visible = TRUE")
//    Page<ArticleEntity> findLastNByIdAndLang(@Param(value = "articleId") UUID articleId,
//                                             @Param(value = "lang") Language language,
//                                             Pageable request);
}
