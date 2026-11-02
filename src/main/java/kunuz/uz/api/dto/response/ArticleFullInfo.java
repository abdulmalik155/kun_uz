package kunuz.uz.api.dto.response;


import kunuz.uz.api.entity.ArticleCategoryEntity;
import kunuz.uz.api.entity.ArticleSectionEntity;
import kunuz.uz.api.entity.ArticleTagEntity;
import kunuz.uz.api.mapper.category.CategoryShort;
import kunuz.uz.api.mapper.section.SectionShort;
import kunuz.uz.api.mapper.tag.TagShort;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ArticleFullInfo {
    private UUID id;

    private String title;

    private String description;

    private String content;

    private Long sharedCount;

    private Integer regionId;

    private List<CategoryShort> categoryList;

    private List<SectionShort> sectionList;

    private LocalDateTime publishedDate;

    private Long viewCount;

    private Long likeCount;

    private List<TagShort> tagList;
}
