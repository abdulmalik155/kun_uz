package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.SectionEntity;
import kunuz.uz.api.mapper.articleType.SectionShort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends CrudRepository<SectionEntity, Integer>, PagingAndSortingRepository<SectionEntity, Integer> {

    @Query(value =
            "SELECT a " +
                    "FROM SectionEntity AS a " +
                    "WHERE a.visible = TRUE " +
                    "ORDER BY a.orderNumber")
    List<SectionEntity> allSortedByOrderNumber();


    @Transactional
    @Modifying
    @Query(value =
            "UPDATE SectionEntity AS a " +
                    "SET a.visible = FALSE " +
                    "WHERE a.id = ?1")
    void updateVisibleById(Integer id);

    @Query(value =
            "SELECT a.id AS id, " +
            "       a.key AS key, " +
            "(CASE ?1 " +
            "   WHEN 'UZ' THEN a.nameUz " +
            "   WHEN 'RU' THEN a.nameRu " +
            "   WHEN 'EN' THEN a.nameEn " +
            "       ELSE a.nameUz " +
            "   END) AS name " +
            " FROM SectionEntity AS a " +
            " WHERE a.visible = TRUE")
    List<SectionShort> getByLanguage(String language);

    Optional<SectionEntity> findByKey(String key);

    Optional<SectionEntity> findByOrderNumber(Integer orderNumber);
}
