package kunuz.uz.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailHistoryResponseDTO {
    private Long id;
    private String email;
    private String message;
    private LocalDateTime createdDate;
}
