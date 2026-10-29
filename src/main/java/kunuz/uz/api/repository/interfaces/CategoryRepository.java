package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.CategoryEntity;
import kunuz.uz.api.mapper.category.CategoryShort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer>, PagingAndSortingRepository<CategoryEntity, Integer> {

    @Query(value =
            " SELECT c.id as id, " +
            " c.key as key, " +
            "(CASE ?1 " +
            "      WHEN 'UZ' THEN c.nameUz" +
            "      WHEN 'RU' THEN c.nameRu" +
            "      WHEN 'EN' THEN c.nameEn" +
            "      ELSE c.nameUz" +
            "      END) as name " +
            "FROM CategoryEntity AS c " +
            "WHERE c.visible = true")
    List<CategoryShort> allByLanguage(String language);


    @Query(value = "SELECT c " +
            "FROM CategoryEntity as c " +
            "WHERE c.visible = TRUE " +
            "ORDER BY c.orderNumber")
    Iterable<CategoryEntity> allSortedByOrder();

    Optional<CategoryEntity> findByKey(String key);

    @Modifying
    @Transactional
    @Query(value = "UPDATE CategoryEntity c " +
            "SET c.visible = false " +
            "WHERE c.id = ?1")
    void updateVisibleById(Integer id);

    Optional<CategoryEntity> findByOrderNumber(Integer orderNumber);

}
