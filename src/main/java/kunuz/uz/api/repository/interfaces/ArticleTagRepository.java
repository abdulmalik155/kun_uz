package kunuz.uz.api.repository.interfaces;

import kunuz.uz.api.entity.ArticleTagEntity;
import kunuz.uz.api.mapper.tag.TagShort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleTagRepository extends CrudRepository<ArticleTagEntity, Integer> {

    @Query(value = "SELECT t.id AS id, " +
            " t.name AS name " +
            " FROM ArticleTagEntity AS t " +
            " WHERE t.articleId = ?1")
    List<TagShort> findAllByArticleId(UUID articleId);
}
