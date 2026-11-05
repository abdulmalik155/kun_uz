package kunuz.uz.api.dto;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class SmsAuthDTO {
    private String email;
    private String password;
}
