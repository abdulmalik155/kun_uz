package kunuz.uz.api.dto.response.profile;

import kunuz.uz.api.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private String phone;
    private ProfileRole role;
    private LocalDateTime createdDate;
}
