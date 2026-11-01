package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.ArticleTagDTO;
import kunuz.uz.api.dto.response.ArticleTagResponseDTO;
import kunuz.uz.api.entity.ArticleTagEntity;
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

    public void create(ArticleTagDTO dto){
        ArticleTagEntity entity = new ArticleTagEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setName(dto.getName());
        repository.save(entity);
    }

    public List<ArticleTagResponseDTO> list(){
        List<ArticleTagResponseDTO> list = new LinkedList<>();
        for (ArticleTagEntity entity : repository.findAll()) {
            ArticleTagResponseDTO dto = new ArticleTagResponseDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            list.add(dto);
        }
        return list;
    }

    public List<TagShort> getAllByArticleIdShort(UUID articleId){
        return repository.findAllByArticleId(articleId);
    }



    // public void create(UUID articleId, List<Integer> sectionList){
    //        for (Integer sectionId : sectionList) {
    //            ArticleSectionEntity entity = new ArticleSectionEntity();
    //            entity.setArticleId(articleId);
    //            entity.setSectionId(sectionId);
    //            repository.save(entity);
    //        }
    //    }
    //
    //    public void merge(UUID articleId, List<Integer> newList){
    //        List<SectionEntity> old = getAllByArticleId(articleId);
    //        List<Integer> oldList = new LinkedList<>();
    //        for (SectionEntity entity : old) {
    //            oldList.add(entity.getId());
    //        }
    //        for (Integer sectionId : newList) {
    //            if(!oldList.contains(sectionId)){
    //                create(articleId, sectionId);
    //            }
    //        }
    //        for (Integer sectionId : oldList) {
    //            if(!newList.contains(sectionId)){
    //                deleteByArticleAndCategoryIds(articleId, sectionId);
    //            }
    //        }
    //    }
    //
    //    private void deleteByArticleAndCategoryIds(UUID articleId, Integer sectionId) {
    //        repository.deleteByArticleAndSectionIds(articleId,sectionId);
    //    }
    //
    //    private void deleteByArticleId(UUID articleId){
    //        repository.deleteByArticleId(articleId);
    //    }
    //
    //    public List<SectionEntity> getAllByArticleId(UUID articleId){
    //        return repository.findAllByArticleId(articleId);
    //    }
    //
    //    public List<SectionResponseDTO> getAllDTOByArticleId(UUID articleId){
    //        List<SectionResponseDTO> responseList = new LinkedList<>();
    //
    //        for (SectionEntity sectionEntity : repository.findAllByArticleId(articleId)) {
    //            responseList.add(util.toDto(sectionEntity));
    //        }
    //        return responseList;
    //    }
    //
    //    public void create(UUID articleId, Integer sectionId){
    //        ArticleSectionEntity entity = new ArticleSectionEntity();
    //        entity.setArticleId(articleId);
    //        entity.setSectionId(sectionId);
    //        repository.save(entity);
    //    }

}
