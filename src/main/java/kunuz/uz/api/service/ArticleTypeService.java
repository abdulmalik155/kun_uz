package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.articleType.ArticleTypeRequestDTO;
import kunuz.uz.api.dto.response.article.ArticleTypeResponseDTO;
import kunuz.uz.api.entity.ArticleTypeEntity;
import kunuz.uz.api.exceptions.articleType.ArticleTypeNotFoundException;
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

    public ArticleTypeResponseDTO create(ArticleTypeRequestDTO dto) {
        ArticleTypeEntity entity = repository.save(ArticleTypeUtil.toEntity(dto, new ArticleTypeEntity()));
        return ArticleTypeUtil.toResponseDto(entity);
    }

    public ArticleTypeResponseDTO update(Integer id, ArticleTypeRequestDTO dto) {
        ArticleTypeEntity entity = ArticleTypeUtil.toEntity(dto, getEntityOrThrow(id));
        repository.save(entity);
        return ArticleTypeUtil.toResponseDto(entity);
    }

    private ArticleTypeEntity getEntityOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ArticleTypeNotFoundException(String.format("Article type with this id was not found %s", id)));
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public Page<ArticleTypeResponseDTO> paging(int page, int size) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ArticleTypeEntity> entities = repository.findAll(request);
        return new PageImpl<>(ArticleTypeUtil.toResponseDtoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }
}
