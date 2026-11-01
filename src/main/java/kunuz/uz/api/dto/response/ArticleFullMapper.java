package kunuz.uz.api.dto.response;


import kunuz.uz.api.entity.ArticleCategoryEntity;
import kunuz.uz.api.entity.ArticleSectionEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ArticleFullMapper {
    //   ArticleFullInfo
    //    id(uuid),title,description,content,shared_count,
    //        region(key,name),categoryList[{key,name},{}], sectionList[{id,name},{id,name}] published_date,view_count,like_count,
    //        tagList[name,name]
    UUID getId();

    String getTitle();

    String getDescription();

    String getContent();

    Long getSharedCount();

    Integer getRegionId();

    List<ArticleCategoryEntity> getCategoryList();

    List<ArticleSectionEntity> getSectionList();

    LocalDateTime getPublishedDate();

    Long getViewCount();

    Long getLikeCount();
}
