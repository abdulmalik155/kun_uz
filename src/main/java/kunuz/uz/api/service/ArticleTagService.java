package kunuz.uz.api.service;

import kunuz.uz.api.dto.response.TagResponseDTO;
import kunuz.uz.api.entity.ArticleTagEntity;
import kunuz.uz.api.entity.TagEntity;
import kunuz.uz.api.mapper.tag.TagShort;
import kunuz.uz.api.repository.interfaces.ArticleTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleTagService {

    @Autowired
    private ArticleTagRepository repository;

    public void create(UUID articleId, Integer tagId) {
        ArticleTagEntity entity = new ArticleTagEntity();
        entity.setArticleId(articleId);
        entity.setTagId(tagId);
        repository.save(entity);
    }

    public List<TagShort> getAllByArticleIdShort(UUID articleId) {
        return repository.getAllByArticleIdShort(articleId);
    }

    public void create(UUID articleId, List<Integer> tagList) {
        for (Integer tagId : tagList) {
            create(articleId, tagId);
        }
    }

    public void merge(UUID articleId, List<Integer> newList) {
        List<TagEntity> old = getAllByArticleId(articleId);
        List<Integer> oldList = new LinkedList<>();
        for (TagEntity entity : old) {
            oldList.add(entity.getId());
        }
        for (Integer categoryId : newList) {
            if (!oldList.contains(categoryId)) {
                create(articleId, categoryId);
            }
        }
        for (Integer categoryId : oldList) {
            if (!newList.contains(categoryId)) {
                deleteByArticleAndTagIds(articleId, categoryId);
            }
        }
    }

    private void deleteByArticleAndTagIds(UUID articleId, Integer tagId) {
        repository.deleteByArticleAndTagIds(articleId, tagId);
    }

    private void deleteByArticleId(UUID articleId) {
        repository.deleteByArticleId(articleId);
    }

    public List<TagEntity> getAllByArticleId(UUID articleId) {
        return repository.findAllByArticleId(articleId);
    }

    public List<TagResponseDTO> getAllDTOByArticleId(UUID articleId) {
        List<TagResponseDTO> response = new LinkedList<>();
        for (TagEntity entity : repository.findAllByArticleId(articleId)) {
            TagResponseDTO dto = new TagResponseDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            response.add(dto);
        }
        return response;
    }

}
