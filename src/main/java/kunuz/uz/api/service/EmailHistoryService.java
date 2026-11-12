package kunuz.uz.api.service;

import kunuz.uz.api.dto.response.EmailHistoryResponseDTO;
import kunuz.uz.api.entity.EmailHistoryEntity;
import kunuz.uz.api.exceptions.AppBadException;
import kunuz.uz.api.repository.interfaces.EmailHistoryRepository;
import kunuz.uz.api.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository repository;

    public void create(String email, String body, String verificationCode) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(email);
        entity.setBody(body);
        entity.setMessage(verificationCode);
        repository.save(entity);
    }

    public List<EmailHistoryResponseDTO> getByEmail(String email) {
        List<EmailHistoryEntity> list = repository.findAllByEmail(email);
        return toDtoList(list);
    }

    public void isSmsSentToAccount(String email, String message) {
        Optional<EmailHistoryEntity> optional = repository.findLastByEmail(email);
        if (optional.isEmpty()) {
             throw new AppBadException("Not completed");
        }
        EmailHistoryEntity entity = optional.get();
        if (!entity.getMessage().equals(message)) {
             throw new AppBadException("Wrong code");
        }
        //   20:32:40          =   20:30.40  + 0:2:00
        LocalDateTime expDate = entity.getCreatedDate().plusMinutes(1);
        // now   20:32:40  >20:31:30 correct and valid      |     now 20:35:30  >  20:32:40 expired
        if (LocalDateTime.now().isAfter(expDate)) {
             throw new AppBadException("Code expired");
        }
    }

    public List<EmailHistoryResponseDTO> toDtoList(List<EmailHistoryEntity> list) {
        List<EmailHistoryResponseDTO> dtoList = new LinkedList<>();

        for (EmailHistoryEntity entity : list) {
            EmailHistoryResponseDTO dto = new EmailHistoryResponseDTO();
            dto.setId(entity.getId());
            dto.setEmail(entity.getEmail());
            dto.setMessage(entity.getMessage());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Page<EmailHistoryResponseDTO> paging(int page, int size) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(
                page,
                size,
                Sort.by("createdDate")
                        .ascending());
        Page<EmailHistoryEntity> entities = repository.findAll(request);
        return new PageImpl<>(
                toDtoList(entities.getContent()),
                entities.getPageable(),
                entities.getTotalElements());
    }

    public List<EmailHistoryResponseDTO> getByDate(LocalDate date) {
        List<EmailHistoryEntity> list = repository.findAllByDate(date);
        return toDtoList(list);
    }

    public void deleteByEmail(String toAccount) {
        repository.deleteByEmail(toAccount);
    }

//    public List<EmailHistoryResponseDTO> getByEmailAndTime(String toAccount, LocalDateTime now) {
//        return toDtoList(repository.findAllByEmailAndTime(toAccount,now);
//    }
}
