package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.ArticleRequestDTO;
import kunuz.uz.api.dto.response.ArticleResponseDTO;
import kunuz.uz.api.entity.ArticleEntity;
import kunuz.uz.api.service.ArticleCategoryService;
import kunuz.uz.api.service.ArticleSectionService;
import kunuz.uz.api.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ArticleUtil implements  BaseEntityUtil<ArticleRequestDTO, ArticleResponseDTO, ArticleEntity>{

    @Autowired
    private ArticleCategoryService categoryService;

    @Autowired
    private ArticleSectionService sectionService;

    @Autowired
    private ArticleTagService tagService;

    @Override
    public ArticleEntity toEntity(ArticleRequestDTO dto, ArticleEntity entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setReadTime(dto.getReadTime());
        return entity;
    }

    @Override
    public ArticleResponseDTO toDto(ArticleEntity entity) {
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCategoryList(categoryService.getAllDTOByArticleId(entity.getId()));
        dto.setSectionList(sectionService.getAllDTOByArticleId(entity.getId()));
        dto.setTagList(tagService.getAllDTOByArticleId(entity.getId()));
        return dto;
    }

    @Override
    public List<ArticleResponseDTO> toDtoList(Iterable<ArticleEntity> iterable) {
        List<ArticleResponseDTO> list = new LinkedList<>();
        for (ArticleEntity entity : iterable) {
            list.add(toDto(entity));
        }
        return list;
    }
}
