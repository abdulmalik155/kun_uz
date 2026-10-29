package kunuz.uz.api.repository.custom.classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import kunuz.uz.api.dto.request.CategoryFilterDTO;
import kunuz.uz.api.entity.CategoryEntity;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.exceptions.articleType.LanguageNotSupportedException;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public Page<Object[]> getByLanguage(int page, int size, Language language) {
        entityManager.flush();
        entityManager.clear();
        System.out.println("Custom builder here");
        StringBuilder selectQueryBuilder = new StringBuilder("select c.id as id ");
        String countQueryBuilder = "select count(c) as count from CategoryEntity c";

        StringBuilder queryBuilder = queryBuilder(language);

        queryBuilder.append(" order by c.orderNumber asc");
        selectQueryBuilder.append(queryBuilder);

        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        Query countQuery = entityManager.createQuery(countQueryBuilder);

        List<Object[]> regionArray = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();
        PageRequest pageRequest = PageRequest.of(page, size);
        return new PageImpl<>(regionArray, pageRequest, totalElements);
    }

    private @NonNull StringBuilder queryBuilder(Language language) {
        StringBuilder queryBuilder = new StringBuilder();
        if (language != null) {
            switch (language) {
                case Language.UZ -> {
                    queryBuilder.append(", c.nameUz as nameUz ");
                    queryBuilder.append(" from CategoryEntity as c");
                }
                case Language.RU -> {
                    queryBuilder.append(", c.nameRu as nameRu ");
                    queryBuilder.append(" from CategoryEntity as c");
                }
                case Language.EN -> {
                    queryBuilder.append(", c.nameEn as nameEn ");
                    queryBuilder.append(" from CategoryEntity as c");
                }
                default -> throw new LanguageNotSupportedException(String.format("Given language %s is not supported", language));
            }
        }
        return queryBuilder;
    }

    public Page<CategoryEntity> findAll(int page, int size, CategoryFilterDTO dto) {
        entityManager.flush();
        entityManager.clear();
        StringBuilder selectBuilder = new StringBuilder("select c from CategoryEntity c");
        StringBuilder countBuilder = new StringBuilder("select count(c) from CategoryEntity c");
        StringBuilder queryBuilder = new StringBuilder(" where c.visible = true");

        Map<String, Object> params = new LinkedHashMap<>();
        if (dto.getNameUz() != null && !dto.getNameUz().isBlank()) {
            queryBuilder.append(" and lower(trim(c.nameUz)) like lower(:nameUz)");
            params.put("nameUz", "%"+dto.getNameUz().trim()+"%");
        }
        if (dto.getNameRu() != null && !dto.getNameRu().isBlank()) {
            queryBuilder.append(" and lower(trim(c.nameRu)) like lower(:nameRu)");
            params.put("nameRu", "%"+dto.getNameRu().trim()+"%");
        }
        if (dto.getNameEn() != null && !dto.getNameEn().isBlank()) {
            queryBuilder.append(" and lower(trim(c.nameEn)) like lower(:nameEn)");
            params.put("nameEn", "%"+dto.getNameEn().trim()+"%");
        }
        if (dto.getVisible() != null) {
            queryBuilder.append(" and c.visible = :visible");
            params.put("visible", dto.getVisible());
        }
        if (dto.getCreatedDateFrom() != null && dto.getCreatedDateFrom().isBefore(LocalDate.now())) {
            queryBuilder.append(" and cast(c.createdDate as date) >= :createdDateFrom");
            params.put("createdDateFrom", dto.getCreatedDateFrom());
        }
        if (dto.getCreatedDateTo() != null) {
            queryBuilder.append(" and cast(c.createdDate as date) < :createdDateTo");
            params.put("createdDateTo", dto.getCreatedDateTo());
        }

        countBuilder.append(queryBuilder);
        queryBuilder.append(" order by c.createdDate desc");
        selectBuilder.append(queryBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        params.forEach(selectQuery::setParameter);

        List<CategoryEntity> contents = selectQuery.getResultList();

        Query countQuery = entityManager.createQuery(countBuilder.toString());
        params.forEach(countQuery::setParameter);
        Long totalElements = (Long) countQuery.getSingleResult();

        PageRequest request = PageRequest.of(page, size);
        return new PageImpl<>(contents, request, totalElements);
    }
}
