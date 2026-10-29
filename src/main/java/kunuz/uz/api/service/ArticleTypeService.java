package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.ArticleTypeRequestDTO;
import kunuz.uz.api.dto.request.ArticleTypeFilterDTO;
import kunuz.uz.api.dto.response.ArticleTypeResponseDTO;
import kunuz.uz.api.entity.ArticleTypeEntity;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.exceptions.articleType.ArticleTypeNotFoundException;
import kunuz.uz.api.mapper.articleType.ArticleTypeShort;
import kunuz.uz.api.repository.custom.classes.ArticleTypeCustomRepository;
import kunuz.uz.api.repository.interfaces.ArticleTypeRepository;
import kunuz.uz.api.util.ArticleTypeUtil;
import kunuz.uz.api.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ArticleTypeService {

    @Autowired
    private ArticleTypeRepository repository;

    @Autowired
    private ArticleTypeCustomRepository customRepository;

    @Autowired
    private ArticleTypeUtil util;

    public ArticleTypeResponseDTO create(ArticleTypeRequestDTO dto) {
        ArticleTypeEntity entity = repository.save(util.toEntity(dto, new ArticleTypeEntity()));
        return util.toDto(entity);
    }

    public ArticleTypeResponseDTO update(Integer id, ArticleTypeRequestDTO dto) {
        ArticleTypeEntity entity = util.toEntity(dto, getEntityOrThrow(id));
        repository.save(entity);
        return util.toDto(entity);
    }

    private ArticleTypeEntity getEntityOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ArticleTypeNotFoundException(String.format("Article type with this id was not found %s", id)));
    }

    public void deleteById(Integer id) {
//        repository.deleteById(id);
        ArticleTypeEntity existingEntity = getEntityOrThrow(id);
        existingEntity.setVisible(false);
        repository.save(existingEntity);
    }

    public Page<ArticleTypeResponseDTO> filter(int page, int size, ArticleTypeFilterDTO dto) {
        PaginationUtil.check(page, size);
        page--;
        Page<ArticleTypeEntity> entities = customRepository.filter(page, size, dto);
        return new PageImpl<>(util.toDtoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    public Page<ArticleTypeShort> getByLanguage(int page, int size, Language language) {
       /* PaginationUtil.check(page, size);
        page--;
        if (language == null) throw new IllegalArgumentException("Language of name of article can not be empty");

        Page<Object[]> entities = customRepository.getByLanguage(page, size, language);

        return new PageImpl<>(entities.getContent(),
                entities.getPageable(),
                entities.getTotalElements());*/
        return null;
    }

    public Page<ArticleTypeResponseDTO> articleTypeList(int page, int size) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ArticleTypeEntity> entities = repository.findAll(request);
        return new PageImpl<>(util
                .toDtoList(entities.getContent()),
                entities.getPageable(),
                entities.getTotalElements());
    }
}
