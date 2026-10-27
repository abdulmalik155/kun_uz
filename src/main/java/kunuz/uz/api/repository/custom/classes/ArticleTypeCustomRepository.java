package kunuz.uz.api.repository.custom.classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import kunuz.uz.api.dto.request.articleType.ArticleTypeRequestDTO;
import kunuz.uz.api.entity.ArticleTypeEntity;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.exceptions.articleType.LanguageNotSupportedException;
import kunuz.uz.api.mapper.articleType.ArticleTypeShort;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleTypeCustomRepository {

    @Autowired
    private EntityManager entityManager;

    //        (id,key,name_uz, name_ru, name_en,visible,created_date)
    public Page<ArticleTypeEntity> filter(int page, int size, ArticleTypeRequestDTO dto) {
        StringBuilder selectBuilder = new StringBuilder("select a from ArticleTypeEntity a");
        StringBuilder countBuilder = new StringBuilder("select count(a) from ArticleTypeEntity a");
        StringBuilder queryBuilder = new StringBuilder(" where a.visible = true ");

        Map<String, Object> params = new LinkedHashMap<>();

        if (dto.getOrderNumber() != null) {
            queryBuilder.append(" and a.orderNumber = :orderNumber");
            params.put("orderNumber", dto.getOrderNumber());
        }
        if (dto.getNameUz() != null) {
            queryBuilder.append(" and a.nameUz = :nameUz");
            params.put("nameUz", dto.getNameUz());
        }
        if (dto.getNameRu() != null) {
            queryBuilder.append(" and a.nameRu = :nameRu");
            params.put("nameRu", dto.getNameRu());
        }
        if (dto.getNameEn() != null) {
            queryBuilder.append(" and a.nameEn = :nameEn");
            params.put("nameEn", dto.getNameEn());
        }

        selectBuilder.append(queryBuilder);
        countBuilder.append(queryBuilder);

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
                default -> throw new LanguageNotSupportedException(String.format("Given language %s is not supported", language));
            }
        }
        return queryBuilder;
    }
}
