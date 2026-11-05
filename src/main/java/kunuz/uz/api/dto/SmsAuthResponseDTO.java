package kunuz.uz.api.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SmsAuthResponseDTO {
    private String message;
    private SmsAuthResponseData data;
}
