package kunuz.uz.api.repository.interfaces;

import jakarta.validation.constraints.NotBlank;
import kunuz.uz.api.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer> {
    Optional<TagEntity> findByName(String name);
}
