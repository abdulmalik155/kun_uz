package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.ArticleTagDTO;
import kunuz.uz.api.dto.response.ArticleResponseDTO;
import kunuz.uz.api.dto.response.ArticleTagResponseDTO;
import kunuz.uz.api.entity.ArticleTagEntity;
import kunuz.uz.api.mapper.tag.TagShort;
import kunuz.uz.api.repository.interfaces.ArticleTagRepository;
import kunuz.uz.api.util.ArticleTagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleTagService {

    @Autowired
    private ArticleTagRepository repository;

    @Autowired
    private ArticleTagUtil util;


    public ArticleTagResponseDTO create(ArticleTagDTO dto){
        ArticleTagEntity entity = util.toEntity(dto, new ArticleTagEntity());
        ArticleTagEntity saved = repository.save(entity);
        return util.toDto(saved);
    }

    public List<ArticleTagResponseDTO> list(){
        return util.toDtoList(repository.findAll());
    }

    public List<TagShort> getAllByArticleIdShort(UUID articleId){
        return repository.findAllByArticleId(articleId);
    }

}
