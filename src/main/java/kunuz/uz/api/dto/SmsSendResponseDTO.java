package kunuz.uz.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SmsSendResponseDTO {

    private UUID id;
    private String message;
    private String status;

}
