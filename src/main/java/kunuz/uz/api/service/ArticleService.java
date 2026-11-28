package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.ArticleListDTO;
import kunuz.uz.api.dto.request.ArticleRequestDTO;
import kunuz.uz.api.dto.response.ArticleFullInfo;
import kunuz.uz.api.dto.response.ArticleResponseDTO;
import kunuz.uz.api.dto.response.ArticleShortInfo;
import kunuz.uz.api.entity.ArticleEntity;
import kunuz.uz.api.enums.ArticleStatus;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.exceptions.AppBadException;
import kunuz.uz.api.exceptions.ArticleNotFoundException;
import kunuz.uz.api.exceptions.UnauthorizedProfileException;
import kunuz.uz.api.mapper.article.ArticleShortMapper;
import kunuz.uz.api.repository.interfaces.ArticleRepository;
import kunuz.uz.api.util.ArticleUtil;
import kunuz.uz.api.util.LanguageUtil;
import kunuz.uz.api.util.PaginationUtil;
import kunuz.uz.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
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

    @Autowired
    private RedisService redisService;

    @Transactional(rollbackFor = {RuntimeException.class})
    public ArticleResponseDTO create(ArticleRequestDTO dto) {
        Long moderatorId = SecurityUtil.currentUserId();
        ArticleEntity newArticle = util.toEntity(dto, new ArticleEntity());
        Language language = LanguageUtil.currentLang();
        newArticle.setLanguage(language);
        newArticle.setModeratorId(moderatorId);
        repository.save(newArticle);
        categoryService.create(newArticle.getId(), dto.getCategoryList());
        sectionService.create(newArticle.getId(), dto.getSectionList());
        tagService.create(newArticle.getId(), dto.getTagList());
        ArticleResponseDTO response = util.toDto(newArticle);
        return response;
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public ArticleResponseDTO update(UUID articleId, ArticleRequestDTO dto) {
        ArticleEntity existing = getOrThrow(articleId);
        Long profileId = SecurityUtil.currentUserId();
        if (!existing.getModeratorId().equals(profileId)) {
            throw new UnauthorizedProfileException("Unauthorized moderator");
        }
        ArticleEntity updated = util.toEntity(dto, existing);
        updated.setUpdatedBy(profileId);
        updated.setUpdatedDate(LocalDateTime.now());
        ArticleEntity saved = repository.save(updated);
        categoryService.merge(articleId, dto.getCategoryList());
        sectionService.merge(articleId, dto.getSectionList());
        tagService.merge(articleId, dto.getTagList());
        ArticleResponseDTO response = util.toDto(saved);
        return response;
    }


    public ArticleEntity getOrThrow(UUID id) {
        return repository.findByIdVisibleTrue(id)
                .orElseThrow(
                        () -> new ArticleNotFoundException("Article not found")
                );
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public String delete(UUID articleId) {
        //gotta save who deleted the article
        //and
        Long articleModeratorId = getOrThrow(articleId).getModeratorId();
        if (!articleModeratorId.equals(SecurityUtil.currentUserId()))
            throw new UnauthorizedProfileException("Unauthorized moderator");
        repository.updateVisibleById(articleId);
        return "Successfully deleted";
    }

    public String changeStatusById(UUID articleId, ArticleStatus status) {
        ArticleEntity existing = getOrThrow(articleId);
        Long publisherId = SecurityUtil.currentUserId();

        if (existing.getPublisherId() != null && !existing.getPublisherId().equals(publisherId)) {
            throw new IllegalArgumentException("Only the one who published it can change it's status");
        }
        if (existing.getModeratorId().equals(publisherId)) {
            throw new IllegalArgumentException("Even if moderator also has publisher role he/she can't publish it at the same time");
        }
        if (existing.getPublishedDate() == null && status.equals(ArticleStatus.NOT_PUBLISHED)) {
            throw new IllegalArgumentException("It's already in not published status(it was not published at all)");
        }
        if (status.equals(ArticleStatus.PUBLISHED) && (existing.getPublishedDate() != null && existing.getStatus().equals(status))) {
            throw new IllegalArgumentException("Kallavaram! it's already published");
        }
        if (status.equals(ArticleStatus.PUBLISHED) && existing.getPublishedDate() == null && existing.getStatus().equals(ArticleStatus.NOT_PUBLISHED)) {
            existing.setPublishedDate(LocalDateTime.now());
        }
        existing.setStatus(status);
        existing.setPublisherId(publisherId);
        repository.save(existing);
        return "Status is changed successfully";
    }

    public Page<ArticleShortInfo> getBySectionId(int page, int size, Integer sectionId) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Language language = LanguageUtil.currentLang();
        Page<ArticleShortMapper> entities = repository.findLastNBySectionId(sectionId, language, request);
        return new PageImpl<>(toShortInfoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    public Page<ArticleShortInfo> getLast12Except(ArticleListDTO dto, int page, int size) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Language language = LanguageUtil.currentLang();
        Page<ArticleShortMapper> entities = repository.findLast12Except(dto.getArticleIds(), language, request);
        return new PageImpl<>(toShortInfoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    public Page<ArticleShortInfo> getByCategoryId(int page, int size, Integer categoryId) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Language language = LanguageUtil.currentLang();
        Page<ArticleShortMapper> entities = repository.findLastNByCategoryId(categoryId, language, request);
        return new PageImpl<>(toShortInfoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    public Page<ArticleFullInfo> getByRegionId(int page, int size, Integer regionId) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Language language = LanguageUtil.currentLang();
        Page<ArticleEntity> entities = repository.findLastNByRegionId(regionId, language, request);
        List<ArticleFullInfo> list = new LinkedList<>();
        for (ArticleEntity entity : entities.getContent()) {
            ArticleFullInfo info = toFullInfo(entity);
            list.add(info);
        }
        return new PageImpl<>(list, entities.getPageable(), entities.getTotalElements());
    }

    /// TODO gotta add the language uz ru en in article entity and then get it by id and lang
    public ArticleFullInfo getByIdAndLang(UUID articleId) {
        Language language = LanguageUtil.currentLang();
        ArticleEntity existing = getOrThrow(articleId);
        if (!existing.getLanguage().equals(language)) {
            throw new AppBadException("Given article is not in chosen language");
        }
        return toFullInfo(existing);
    }

    public Page<ArticleShortInfo> getByTagName(int page, int size, String tagName) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Language language = LanguageUtil.currentLang();
        Page<ArticleShortMapper> entities = repository.findLastNByTagName(tagName.trim(), language, request);
        return new PageImpl<>(toShortInfoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    private ArticleShortInfo toShortInfo(ArticleShortMapper mapper) {
        ArticleShortInfo shortInfo = new ArticleShortInfo();
        shortInfo.setId(mapper.getId());
        shortInfo.setTitle(mapper.getTitle());
        shortInfo.setDescription(mapper.getDescription());
//        shortInfo.setImage(mapper.getImage().toResponseDTO());
        shortInfo.setPublishedDate(mapper.getPublishedDate());
        return shortInfo;
    }

    private List<ArticleShortInfo> toShortInfoList(List<ArticleShortMapper> mapperList) {
        List<ArticleShortInfo> shortInfoList = new LinkedList<>();
        for (ArticleShortMapper mapper : mapperList) {
            ArticleShortInfo info = toShortInfo(mapper);
            shortInfoList.add(info);
        }
        return shortInfoList;
    }

    private ArticleFullInfo toFullInfo(ArticleEntity entity) {
        //TODO gotta fix n + 1 problem here
        Language language = LanguageUtil.currentLang();
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

    public Page<ArticleShortInfo> getLast4BySectionId(UUID articleId, Integer sectionId, int page, int size) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Language language = LanguageUtil.currentLang();
        Page<ArticleShortMapper> entities = repository.findLast4BySectionIdExcept(sectionId, articleId, language, request);
        return new PageImpl<>(toShortInfoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void incrementViewIfUnique(UUID articleId, String clientIp) {
        String key = "article:view:" + articleId + ":" + clientIp;
        try {
            boolean saved = redisService.saveIfAbsent(key, LocalDateTime.now().toString(), Duration.ofMinutes(3));
            if (!saved) {
                return;
            }
        } catch (RedisConnectionFailureException e) {
            e.printStackTrace();
            System.out.println("Iye exceptionku bu incrementViewIfUnique : RedisConnectionFailureException");
            throw new RuntimeException("Something went wrong :(", e);
        }

        int updated = repository.increaseViewCount(articleId);
        if (updated == 0) {
            redisService.delete(key);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void incrementShareCount(UUID articleId, String clientIp) {
        String key = "article:share:" + articleId + ":" + clientIp;

        try {
            String sharedCountStr = redisService.get(key);
            if(sharedCountStr != null){
                if(Long.parseLong(sharedCountStr) == 5){
                    throw new AppBadException("Sharing is temporarily not allowed");
                }
            }
             redisService.increment(key,  Duration.ofHours(1));
        } catch (RedisConnectionFailureException e) {
            e.printStackTrace();
            System.out.println("Iye exceptionku bu incrementShareCount : RedisConnectionFailureException");
            throw new RuntimeException("Something went wrong :(", e);
        }
        int updated = repository.increaseShareCount(articleId);
        if(updated == 0){
            redisService.delete(key);
        }
    }
}
