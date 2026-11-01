package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.ArticleSectionEntity;
import kunuz.uz.api.entity.SectionEntity;
import kunuz.uz.api.mapper.section.SectionShort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleSectionRepository extends CrudRepository<ArticleSectionEntity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ArticleSectionEntity AS a WHERE a.articleId = ?1 AND a.sectionId = ?2 ")
    void deleteByArticleAndSectionIds(UUID articleId, Integer sectionId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ArticleSectionEntity AS a WHERE a.articleId = ?1 ")
    void deleteByArticleId(UUID articleId);

    @Query(value = "SELECT a.section FROM ArticleSectionEntity AS a WHERE a.articleId = ?1 ")
    List<SectionEntity> findAllByArticleId(UUID articleId);

    @Query(value = "SELECT s.id AS id, " +
            " sn.key AS key, " +
            " sn.nameUz AS name " +
            " FROM ArticleSectionEntity AS s  " +
            " INNER JOIN s.section AS sn" +
            " WHERE s.articleId = ?1")
    List<SectionShort> getAllByArticleIdShort(UUID articleId);
}
