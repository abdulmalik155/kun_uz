package kunuz.uz.api.repository.interfaces;

import kunuz.uz.api.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer> {
}
