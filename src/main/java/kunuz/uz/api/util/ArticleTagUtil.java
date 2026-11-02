package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.ArticleTagDTO;
import kunuz.uz.api.dto.response.ArticleTagResponseDTO;
import kunuz.uz.api.entity.ArticleTagEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ArticleTagUtil implements BaseEntityUtil<ArticleTagDTO, ArticleTagResponseDTO, ArticleTagEntity>{

    @Override
    public ArticleTagEntity toEntity(ArticleTagDTO dto, ArticleTagEntity entity) {
        entity.setArticleId(dto.getArticleId());
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public ArticleTagResponseDTO toDto(ArticleTagEntity entity) {
        ArticleTagResponseDTO dto = new ArticleTagResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public List<ArticleTagResponseDTO> toDtoList(Iterable<ArticleTagEntity> iterable) {
        List<ArticleTagResponseDTO> list = new LinkedList<>();
        for (ArticleTagEntity entity : iterable) {
            list.add(toDto(entity));
        }
        return list;
    }
}
