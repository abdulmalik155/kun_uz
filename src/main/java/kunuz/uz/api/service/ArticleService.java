package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.ArticleRequestDTO;
import kunuz.uz.api.dto.response.ArticleResponseDTO;
import kunuz.uz.api.dto.response.CategoryResponseDTO;
import kunuz.uz.api.entity.ArticleEntity;
import kunuz.uz.api.entity.CategoryEntity;
import kunuz.uz.api.exceptions.ArticleNotFoundException;
import kunuz.uz.api.repository.interfaces.ArticleRepository;
import kunuz.uz.api.util.ArticleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private ArticleUtil util;

    @Autowired
    private ArticleCategoryService categoryService;

    @Autowired
    private ArticleSectionService sectionService;

    public ArticleResponseDTO create(ArticleRequestDTO dto) {
        ArticleEntity newArticle = util.toEntity(dto, new ArticleEntity());
        repository.save(newArticle);
        categoryService.create(newArticle.getId(), dto.getCategoryList());
        sectionService.create(newArticle.getId(), dto.getSectionList());
        ArticleResponseDTO response = util.toDto(newArticle);
        return response;
    }


    public ArticleResponseDTO update(UUID id, ArticleRequestDTO dto) {
        ArticleEntity existing = getOrThrow(id);
        ArticleEntity updated = repository.save(util.toEntity(dto,existing));
        categoryService.merge(id, dto.getCategoryList());
        sectionService.merge(id, dto.getSectionList());
        ArticleResponseDTO response = util.toDto(updated);
        return response;
    }

    public ArticleEntity getOrThrow(UUID id){
        return repository.findById(id).orElseThrow(()->new ArticleNotFoundException("Article not found"));
    }
}
