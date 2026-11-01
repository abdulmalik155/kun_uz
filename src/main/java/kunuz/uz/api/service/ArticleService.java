package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.ArticleRequestDTO;
import kunuz.uz.api.dto.response.ArticleResponseDTO;
import kunuz.uz.api.entity.ArticleEntity;
import kunuz.uz.api.enums.ArticleStatus;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.exceptions.ArticleExistsException;
import kunuz.uz.api.exceptions.ArticleNotFoundException;
import kunuz.uz.api.repository.interfaces.ArticleRepository;
import kunuz.uz.api.util.ArticleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public ArticleResponseDTO update(UUID id, ArticleRequestDTO dto) {
        ArticleEntity existing = getOrThrow(id);
        ArticleEntity updated = repository.save(util.toEntity(dto, existing));
        categoryService.merge(id, dto.getCategoryList());
        sectionService.merge(id, dto.getSectionList());
        ArticleResponseDTO response = util.toDto(updated);
        return response;
    }

    public ArticleEntity getOrThrow(UUID id) {
        return repository.findByIdVisibleTrue(id).orElseThrow(() -> new ArticleNotFoundException("Article not found"));
    }

    public String delete(UUID id) {
        repository.updateVisibleById(id);
        return "Successfully deleted";
    }

    public String changeStatusById(UUID articleId, Long publisherId, ArticleStatus status) {
        if(!roleService.getAllByProfileId(publisherId).contains(ProfileRole.ROLE_PUBLISHER)){
            throw new IllegalArgumentException("This is not publisher");
        }
        ArticleEntity existing = getOrThrow(articleId);
        if(existing.getPublisherId() != null && !existing.getPublisherId().equals(publisherId)){
            throw new IllegalArgumentException("Only the one who published it can change it's status");
        }
        existing.setStatus(status);
        existing.setPublisherId(publisherId);
        repository.save(existing);
        return "Status is changed successfully";
    }
}
