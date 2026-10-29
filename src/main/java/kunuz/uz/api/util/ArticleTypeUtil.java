package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.ArticleTypeRequestDTO;
import kunuz.uz.api.dto.response.ArticleTypeResponseDTO;
import kunuz.uz.api.entity.ArticleTypeEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ArticleTypeUtil implements BaseEntityUtil<ArticleTypeRequestDTO, ArticleTypeResponseDTO, ArticleTypeEntity> {

    public ArticleTypeEntity toEntity(ArticleTypeRequestDTO dto, ArticleTypeEntity entity) {
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        if (entity.getId() == null) {
            entity.setVisible(true);
        }
        return entity;
    }

    public ArticleTypeResponseDTO toDto(ArticleTypeEntity entity) {
        ArticleTypeResponseDTO dto = new ArticleTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public List<ArticleTypeResponseDTO> toDtoList(Iterable<ArticleTypeEntity> iterable) {
        List<ArticleTypeResponseDTO> list = new LinkedList<>();
        for (ArticleTypeEntity articleTypeEntity : iterable) {
            list.add(toDto(articleTypeEntity));
        }
        return list;
    }

}
