package kunuz.uz.api.dto.request.profile.filter;

import kunuz.uz.api.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private ProfileRole role;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;

}
