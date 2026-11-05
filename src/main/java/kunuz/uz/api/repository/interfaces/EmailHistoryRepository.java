package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Long>, PagingAndSortingRepository<EmailHistoryEntity, Long> {

    @Query(value = "FROM EmailHistoryEntity AS e " +
            " WHERE e.email = ?1 " +
            " ORDER BY e.createdDate ASC")
    List<EmailHistoryEntity> findAllByEmail(String email);

    @Query(value = "SELECT e " +
            " FROM EmailHistoryEntity AS e " +
            " WHERE cast(e.createdDate AS DATE) = ?1" +
            " ORDER BY e.createdDate ASC")
    List<EmailHistoryEntity> findAllByDate(LocalDate date);

    @Transactional
    @Modifying
    @Query(value = "DELETE " +
            " FROM EmailHistoryEntity AS e " +
            " WHERE e.email = ?1")
    void deleteByEmail(String toAccount);

    Optional<EmailHistoryEntity> findTopByEmailOrderByCreatedDateDesc(String account);

    @Query("from EmailHistoryEntity where email = ?1 order by createdDate desc limit 1")
    Optional<EmailHistoryEntity> findLastByEmail(String email);
}
