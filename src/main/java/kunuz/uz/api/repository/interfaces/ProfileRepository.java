package kunuz.uz.api.repository.interfaces;


import kunuz.uz.api.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Long>, PagingAndSortingRepository<ProfileEntity, Long> {
}
