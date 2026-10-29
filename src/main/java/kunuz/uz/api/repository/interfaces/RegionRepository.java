package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kunuz.uz.api.entity.RegionEntity;
import kunuz.uz.api.mapper.region.RegionShort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository  extends CrudRepository<RegionEntity, Integer>, PagingAndSortingRepository<RegionEntity, Integer> {

    @Query(value = "SELECT r " +
            "FROM RegionEntity AS r " +
            "WHERE r.visible = TRUE " +
            "ORDER BY r.createdDate DESC")
    List<RegionEntity> allSortedByCreatedDate();

    @Query(value =
            "SELECT r.id AS id, r.key AS key, " +
            "(CASE ?1 " +
                "WHEN 'UZ' THEN r.nameUz " +
                "WHEN 'RU' THEN r.nameRu " +
                "WHEN 'EN' THEN r.nameEn " +
                "ELSE r.nameUz " +
            "END) AS name " +
            "FROM RegionEntity AS r " +
            "WHERE r.visible = TRUE")
    List<RegionShort> getByLanguage(String language);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE RegionEntity AS r " +
                "SET r.visible = FALSE " +
                "WHERE r.id = ?1")
    void updateVisibleById(Integer id);

    Optional<RegionEntity> findByKey(String key);

    Optional<RegionEntity> findByOrderNumber(Integer orderNumber);
}
