package kunuz.uz.api.repository.custom.classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import kunuz.uz.api.dto.request.profile.ProfileFilterDTO;
import kunuz.uz.api.entity.ProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public Page<ProfileEntity> filter(int page, int size, ProfileFilterDTO dto) {
        StringBuilder selectQueryBuilder = new StringBuilder("select p from ProfileEntity p ");
        StringBuilder countQueryBuilder = new StringBuilder("select count(p) from ProfileEntity p ");

        StringBuilder queryBuilder = new StringBuilder(" where p.visible = true ");
        Map<String, Object> params = new LinkedHashMap<>();

        if (dto.getName() != null && !dto.getName().isBlank()) {
            queryBuilder.append(" and p.name = :name");
            params.put("name", dto.getName());
        }
        if (dto.getSurname() != null && !dto.getSurname().isBlank()) {
            queryBuilder.append(" and p.surname = :surname");
            params.put("surname", dto.getSurname());
        }
        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            queryBuilder.append(" and p.phone = :phone");
            params.put("phone", dto.getUsername());
        }
        if (dto.getRole() != null) {
            queryBuilder.append(" and p.role = :role");
            params.put("role", dto.getRole());
        }
        if (dto.getCreatedDateFrom() != null) {
            queryBuilder.append(" and cast(p.createdDate as date) >= :createdDateFrom");
            params.put("createdDateFrom", dto.getCreatedDateFrom());
        }
        if (dto.getCreatedDateTo() != null) {
            queryBuilder.append(" and cast(p.createdDate as date) < :createdDateTo");
            params.put("createdDateTo", dto.getCreatedDateTo());
        }

        countQueryBuilder.append(queryBuilder);
        queryBuilder.append(" order by p.name asc, p.createdDate desc");
        selectQueryBuilder.append(queryBuilder);

        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult(page*size);
        selectQuery.setMaxResults(size);
        params.forEach(selectQuery::setParameter);

        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        List<ProfileEntity> list = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();
        PageRequest request = PageRequest.of(page, size);
        return new PageImpl<>(list, request, totalElements);
    }

}
