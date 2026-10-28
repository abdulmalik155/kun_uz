package kunuz.uz.api.repository.custom.classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import kunuz.uz.api.dto.request.articleType.filter.ArticleTypeFilterDTO;
import kunuz.uz.api.entity.ArticleTypeEntity;
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
public class ArticleTypeCustomRepository {

    @Autowired
    private EntityManager entityManager;

    //        (id,key,name_uz, name_ru, name_en,visible,created_date)
    public Page<ArticleTypeEntity> filter(int page, int size, ArticleTypeFilterDTO dto) {
        StringBuilder selectBuilder = new StringBuilder("select a from ArticleTypeEntity a");
        StringBuilder countBuilder = new StringBuilder("select count(a) from ArticleTypeEntity a");
        StringBuilder queryBuilder = new StringBuilder(" where 1 = 1 ");

        Map<String, Object> params = new LinkedHashMap<>();

        if (dto.getNameUz() != null) {
            queryBuilder.append(" and lower(trim(a.nameUz)) like :nameUz");
            params.put("nameUz", "%" + dto.getNameUz().trim().toLowerCase() + "%");
        }
        if (dto.getNameRu() != null) {
            queryBuilder.append(" and lower(trim(a.nameRu)) like :nameRu");
            params.put("nameRu", "%" + dto.getNameRu().trim().toLowerCase() + "%");
        }
        if (dto.getNameEn() != null) {
            queryBuilder.append(" and lower(trim(a.nameEn)) like :nameEn");
            params.put("nameEn", "%" + dto.getNameEn().trim().toLowerCase() + "%");
        }
        if (dto.getVisible() != null) {
            queryBuilder.append(" and a.visible = :visible");
            params.put("visible", dto.getVisible());
        }
        if (dto.getCreatedDateFrom() != null && dto.getCreatedDateFrom().isBefore(LocalDate.now().plusDays(1))){
            queryBuilder.append(" and cast(a.createdDate as date) >= :createdDateFrom");
            params.put("createdDateFrom", dto.getCreatedDateFrom());
        }
        if (dto.getCreatedDateTo() != null){
            queryBuilder.append(" and cast(a.createdDate as date) < :createdDateTo");
            params.put("createdDateTo", dto.getCreatedDateTo());
        }

        countBuilder.append(queryBuilder);
        queryBuilder.append(" order by a.orderNumber asc");
        selectBuilder.append(queryBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        params.forEach(selectQuery::setParameter);

        Query countQuery = entityManager.createQuery(countBuilder.toString());
        params.forEach(countQuery::setParameter);

        List<ArticleTypeEntity> list = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        PageRequest request = PageRequest.of(page, size);
        return new PageImpl<>(list, request, totalElements);
    }

    public Page<Object[]> getByLanguage(int page, int size, Language language) {
        StringBuilder selectQueryBuilder = new StringBuilder("select a.id as id ");
        String countQueryBuilder = "select count(a) as count from ArticleTypeEntity a";
        StringBuilder queryBuilder = queryBuilder(language);
        queryBuilder.append(" order by a.orderNumber asc");
        selectQueryBuilder.append(queryBuilder);

        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        Query countQuery = entityManager.createQuery(countQueryBuilder);

        List<Object[]> arrayArticleType = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        PageRequest pageRequest = PageRequest.of(page, size);
        return new PageImpl<>(arrayArticleType, pageRequest, totalElements);
    }

    private static @NonNull StringBuilder queryBuilder(Language language) {
        StringBuilder queryBuilder = new StringBuilder();
        if (language != null) {
            switch (language) {
                case Language.UZ -> {
                    queryBuilder.append(", a.nameUz as nameUz ");
                    queryBuilder.append(" from ArticleTypeEntity as a");
                }
                case Language.RU -> {
                    queryBuilder.append(", a.nameRu as nameRu ");
                    queryBuilder.append(" from ArticleTypeEntity as a");
                }
                case Language.EN -> {
                    queryBuilder.append(", a.nameEn as nameEn ");
                    queryBuilder.append(" from ArticleTypeEntity as a");
                }
                default ->
                        throw new LanguageNotSupportedException(String.format("Given language %s is not supported", language));
            }
        }
        return queryBuilder;
    }
}
