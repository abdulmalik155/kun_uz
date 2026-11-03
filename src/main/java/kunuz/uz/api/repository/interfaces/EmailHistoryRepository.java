package kunuz.uz.api.repository.interfaces;

import jakarta.transaction.Transactional;
import kunuz.uz.api.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

//    @Query(value = "SLE")
//    List<EmailHistoryEntity> findAllByEmailAndTime(@Param(value = "email") String toAccount,
//                                                   @Param(value = "time") LocalDateTime now);
}
