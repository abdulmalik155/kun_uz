package kunuz.uz.api.repository.interfaces;


import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import kunuz.uz.api.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Long>, PagingAndSortingRepository<ProfileEntity, Long> {



    @Query(value = "SELECT p FROM ProfileEntity AS p WHERE p.username = ?1 AND p.visible = TRUE")
    Optional<ProfileEntity> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ProfileEntity AS p SET p.visible = FALSE WHERE p.id = ?1")
    void updateVisibleById(Long id);

    @Query(value = "SELECT p FROM ProfileEntity AS p INNER JOIN FETCH p.roleList WHERE p.visible = TRUE")
    Page<ProfileEntity> findAllWithRole(Pageable pageable);
}
