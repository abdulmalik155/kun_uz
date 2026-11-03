package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.ArticleTagEntity;
import kunuz.uz.api.entity.TagEntity;
import kunuz.uz.api.mapper.tag.TagShort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleTagRepository extends CrudRepository<ArticleTagEntity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE " +
            " FROM ArticleTagEntity AS at " +
            " WHERE at.articleId = :article_id " +
            " AND at.tagId = :tag_id")
    void deleteByArticleAndTagIds(@Param(value = "article_id") UUID articleId,
                                  @Param(value = "tag_id") Integer tagId);

    void deleteByArticleId(UUID articleId);

    @Query(value = "SELECT t.id AS id, " +
            " t.name AS name " +
            " FROM ArticleTagEntity AS at " +
            " INNER JOIN at.tag AS t " +
            " WHERE at.articleId = ?1")
    List<TagShort> getAllByArticleIdShort(UUID articleId);

    @Query(value = "SELECT t " +
            " FROM ArticleTagEntity AS at " +
            " INNER JOIN at.tag AS t " +
            " WHERE at.articleId = ?1 ")
    List<TagEntity> findAllByArticleId(UUID articleId);

}
