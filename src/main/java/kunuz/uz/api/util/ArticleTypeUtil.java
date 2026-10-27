package kunuz.uz.api.util;

import kunuz.uz.api.dto.request.articleType.ArticleTypeRequestDTO;
import kunuz.uz.api.dto.response.article.ArticleTypeResponseDTO;
import kunuz.uz.api.entity.ArticleTypeEntity;
import kunuz.uz.api.mapper.articleType.ArticleTypeShort;

import java.util.LinkedList;
import java.util.List;

public class ArticleTypeUtil {

    public static ArticleTypeEntity toEntity(ArticleTypeRequestDTO dto, ArticleTypeEntity entity){
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        if(entity.getId() == null){
            entity.setVisible(true);
        }
        return entity;
    }

    public static ArticleTypeResponseDTO toResponseDto(ArticleTypeEntity entity){
        ArticleTypeResponseDTO dto = new ArticleTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public static List<ArticleTypeResponseDTO> toResponseDtoList(Iterable<ArticleTypeEntity> iterable){
        List<ArticleTypeResponseDTO> list = new LinkedList<>();
        for (ArticleTypeEntity articleTypeEntity : iterable) {
            list.add(toResponseDto(articleTypeEntity));
        }
        return list;
    }

    public static List<ArticleTypeShort> shortToResponseDtoList(Iterable <Object[]> iterable){
        List<ArticleTypeShort> list = new LinkedList<>();
        for (Object[] articleType : iterable) {
            ArticleTypeShort dto = new ArticleTypeShort();
            dto.setId((Integer) articleType[0]);
            dto.setName((String) articleType[1]);
            list.add(dto);
        }
        return list;
    }

}
