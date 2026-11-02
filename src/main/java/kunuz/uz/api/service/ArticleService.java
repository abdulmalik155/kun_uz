package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.ArticleListDTO;
import kunuz.uz.api.dto.request.ArticleRequestDTO;
import kunuz.uz.api.dto.response.ArticleResponseDTO;
import kunuz.uz.api.entity.ArticleEntity;
import kunuz.uz.api.enums.ArticleStatus;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.exceptions.ArticleExistsException;
import kunuz.uz.api.exceptions.ArticleNotFoundException;
import kunuz.uz.api.dto.response.ArticleFullInfo;
import kunuz.uz.api.mapper.article.ArticleShortInfo;
import kunuz.uz.api.repository.interfaces.ArticleRepository;
import kunuz.uz.api.util.ArticleUtil;
import kunuz.uz.api.util.PaginationUtil;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private ProfileRoleService roleService;

    @Autowired
    private ArticleTagService tagService;

    public ArticleResponseDTO create(Long moderatorId, ArticleRequestDTO dto) {
        Optional<ArticleEntity> existsOptional = repository.findDuplicate(moderatorId,
                dto.getRegionId(),
                dto.getTitle().trim(),
                dto.getDescription().trim(),
                dto.getContent().trim());
        if (existsOptional.isPresent()){
            throw new ArticleExistsException("Article exists");
        }
        if(!roleService.getAllByProfileId(moderatorId).contains(ProfileRole.ROLE_MODERATOR)){
            throw new IllegalArgumentException("This is not a journalist(moderator)");
        }
        ArticleEntity newArticle = util.toEntity(dto, new ArticleEntity());
        newArticle.setModeratorId(moderatorId);
        repository.save(newArticle);
        categoryService.create(newArticle.getId(), dto.getCategoryList());
        sectionService.create(newArticle.getId(), dto.getSectionList());
        ArticleResponseDTO response = util.toDto(newArticle);
        return response;
    }


    public ArticleResponseDTO update(UUID articleId, ArticleRequestDTO dto) {
        ArticleEntity existing = getOrThrow(articleId);
        ArticleEntity updated = repository.save(util.toEntity(dto, existing));
        categoryService.merge(articleId, dto.getCategoryList());
        sectionService.merge(articleId, dto.getSectionList());
        ArticleResponseDTO response = util.toDto(updated);
        return response;
    }

    public ArticleEntity getOrThrow(UUID id) {
        return repository.findByIdVisibleTrue(id)
                .orElseThrow(
                        () -> new ArticleNotFoundException("Article not found")
                );
    }

    public String delete(UUID id) {
        repository.updateVisibleById(id);
        return "Successfully deleted";
    }

    public String changeStatusById(UUID articleId, Long publisherId, ArticleStatus status) {
        ArticleEntity existing = getOrThrow(articleId);
        if(!roleService.getAllByProfileId(publisherId).contains(ProfileRole.ROLE_PUBLISHER)){
            throw new IllegalArgumentException("This is not publisher");
        }
        if(existing.getPublisherId() != null && !existing.getPublisherId().equals(publisherId)){
            throw new IllegalArgumentException("Only the one who published it can change it's status");
        }
        if(existing.getModeratorId().equals(publisherId)){
            throw new IllegalArgumentException("Even if moderator also has publisher role he/she can't publish it at the same time");
        }
        if(existing.getPublishedDate() == null && status.equals(ArticleStatus.NOT_PUBLISHED)){
            throw new IllegalArgumentException("It's already in not published status(it was not published at all)");
        }
        if(status.equals(ArticleStatus.PUBLISHED) && existing.getPublishedDate() != null && existing.getStatus().equals(ArticleStatus.PUBLISHED)){
            throw new IllegalArgumentException("Kallavaram! it's already published");
        }
        if(status.equals(ArticleStatus.PUBLISHED)){
            existing.setPublishedDate(LocalDateTime.now());
        }
        existing.setStatus(status);
        existing.setPublisherId(publisherId);
        repository.save(existing);
        return "Status is changed successfully";
    }

    public Page<ArticleShortInfo> getBySectionId(int page, int size, Integer sectionId) {
        PaginationUtil.check(page,size);
        page--;
        PageRequest request = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<ArticleShortInfo> entities = repository.findLastNBySectionId(sectionId, request);
        return entities;
    }

    public Page<ArticleShortInfo> getLast12Except(ArticleListDTO dto, int page, int size) {
        PaginationUtil.check(page,size);
        page--;
        PageRequest request = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<ArticleShortInfo> entities = repository.findLast12Except(dto.getArticleIds(), request);
        return entities;
    }

    public Page<ArticleShortInfo> getByCategoryId(int page, int size, Integer categoryId) {
        PaginationUtil.check(page,size);
        page--;
        PageRequest request = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<ArticleShortInfo> entities = repository.findLastNByCategoryId(categoryId, request);
        return entities;
    }

    public Page<ArticleFullInfo> getByRegionId(int page, int size, Integer regionId) {
        PaginationUtil.check(page,size);
        page--;
        PageRequest request = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<ArticleEntity> entities = repository.findLastNByRegionId(regionId, request);
        List<ArticleFullInfo> list = new LinkedList<>();
        for (ArticleEntity entity : entities.getContent()) {
            ArticleFullInfo info = toFullInfo(entity, Language.UZ.toString());
            list.add(info);
        }
        return new PageImpl<>(list, entities.getPageable(), entities.getTotalElements());
    }

    private ArticleFullInfo toFullInfo(ArticleEntity entity, String language) {
        ArticleFullInfo info = new ArticleFullInfo();
        info.setId(entity.getId());
        info.setTitle(entity.getTitle());
        info.setDescription(entity.getDescription());
        info.setContent(entity.getContent());
        info.setSharedCount(entity.getSharedCount());
        info.setRegionId(entity.getRegionId());
        info.setCategoryList(categoryService.findAllByArticleIdAndLang(entity.getId(), language));
        info.setSectionList(sectionService.findAllByArticleIdAndLang(entity.getId(), language));
        info.setViewCount(entity.getViewCount());
        info.setLikeCount(entity.getLikeCount());
        info.setTagList(tagService.getAllByArticleIdShort(entity.getId()));
        return info;
    }

    public ArticleFullInfo getByIdAndLang( UUID articleId, Language language) {
        ArticleEntity existing = getOrThrow(articleId);
        return toFullInfo(existing, language.toString());
    }

    public Page<ArticleFullInfo> getByTag(int page, int size, String name) {
        PaginationUtil.check(page,size);
        page--;
        PageRequest request = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<ArticleEntity> entities = repository.findLastNByTag(name, request);
        List<ArticleFullInfo> list = new LinkedList<>();
        for (ArticleEntity entity : entities.getContent()) {
            ArticleFullInfo info = toFullInfo(entity, Language.UZ.toString());
            list.add(info);
        }
        return new PageImpl<>(list, entities.getPageable(), entities.getTotalElements());
    }
}
