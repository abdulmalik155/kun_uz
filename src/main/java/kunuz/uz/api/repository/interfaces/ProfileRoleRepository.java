package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.ProfileRoleEntity;
import kunuz.uz.api.enums.ProfileRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {

    @Query(value = "SELECT pr.role FROM ProfileRoleEntity AS pr WHERE pr.profileId = ?1")
    List<ProfileRole> findAllByProfileId(Long profileId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ProfileRoleEntity AS pr WHERE pr.profileId = :profileId AND pr.role = :role")
    void deleteByProfileIdAndRole(@Param(value = ":profileId") Long profileId,@Param(value = "role") ProfileRole role);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ProfileRoleEntity AS pr WHERE pr.profileId = ?1")
    void deleteByProfileId(Long profileId);
}
