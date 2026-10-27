package kunuz.uz.api.repository.custom.classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import kunuz.uz.api.dto.request.region.filter.RegionFilterDTO;
import kunuz.uz.api.entity.RegionEntity;
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
public class RegionCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public Page<RegionEntity> findAll(int page, int size, RegionFilterDTO dto) {
        StringBuilder selectBuilder = new StringBuilder("select r from RegionEntity r");
        StringBuilder countBuilder = new StringBuilder("select count(r) from RegionEntity r");
        StringBuilder queryBuilder = new StringBuilder(" where r.visible = true");

        Map<String, Object> params = new LinkedHashMap<>();
        if (dto.getNameUz() != null && !dto.getNameUz().isBlank()) {
            queryBuilder.append(" and lower(trim(r.nameUz)) like lower(:nameUz)");
            params.put("nameUz", "%"+dto.getNameUz().trim()+"%");
        }
        if (dto.getNameRu() != null && !dto.getNameRu().isBlank()) {
            queryBuilder.append(" and lower(trim(r.nameRu)) like lower(:nameRu)");
            params.put("nameRu", "%"+dto.getNameRu().trim()+"%");
        }
        if (dto.getNameEn() != null && !dto.getNameEn().isBlank()) {
            queryBuilder.append(" and lower(trim(r.nameEn)) like lower(:nameEn)");
            params.put("nameEn", "%"+dto.getNameEn().trim()+"%");
        }
        if (dto.getVisible() != null) {
            queryBuilder.append(" and r.visible = :visible");
            params.put("visible", dto.getVisible());
        }
        if (dto.getCreatedDateFrom() != null && dto.getCreatedDateFrom().isBefore(LocalDate.now())) {
            queryBuilder.append(" and cast(r.createdDate as date) >= :createdDateFrom");
            params.put("createdDateFrom", dto.getCreatedDateFrom());
        }
        if (dto.getCreatedDateTo() != null) {
            queryBuilder.append(" and cast(r.createdDate as date) < :createdDateTo");
            params.put("createdDateTo", dto.getCreatedDateTo());
        }

        countBuilder.append(queryBuilder);
        queryBuilder.append(" order by r.createdDate desc");
        selectBuilder.append(queryBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        params.forEach(selectQuery::setParameter);
        List<RegionEntity> contents = selectQuery.getResultList();

        Query countQuery = entityManager.createQuery(countBuilder.toString());
        params.forEach(countQuery::setParameter);
        Long totalElements = (Long) countQuery.getSingleResult();

        PageRequest request = PageRequest.of(page, size);
        return new PageImpl<>(contents, request, totalElements);
    }

    public Page<Object[]> getByLanguage(int page, int size, Language language) {
        StringBuilder selectQueryBuilder = new StringBuilder("select r.id as id ");
        String countQueryBuilder = "select count(r) as count from RegionEntity r";
        StringBuilder queryBuilder = queryBuilder(language);
        queryBuilder.append(" order by r.orderNumber asc");
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
                    queryBuilder.append(", r.nameUz as nameUz ");
                    queryBuilder.append(" from RegionEntity as r");
                }
                case Language.RU -> {
                    queryBuilder.append(", r.nameRu as nameRu ");
                    queryBuilder.append(" from RegionEntity as r");
                }
                case Language.EN -> {
                    queryBuilder.append(", r.nameEn as nameEn ");
                    queryBuilder.append(" from RegionEntity as r");
                }
                default -> throw new LanguageNotSupportedException(String.format("Given language %s is not supported", language));
            }
        }
        return queryBuilder;
    }
}
