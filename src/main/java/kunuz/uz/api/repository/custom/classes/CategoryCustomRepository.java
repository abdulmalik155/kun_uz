package kunuz.uz.api.repository.custom.classes;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryCustomRepository {

    @Autowired
    private EntityManager entityManager;
}
