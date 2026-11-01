package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.ArticleSectionEntity;
import kunuz.uz.api.entity.SectionEntity;
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
}
