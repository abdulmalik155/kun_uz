package kunuz.uz.api.dto.request.profile;


import jakarta.validation.constraints.*;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDTO extends BaseProfileDTO {

    @NotNull(message = "Profile status is required")
    private ProfileStatus status;

    @NotNull(message = "Profile role is required")
    private ProfileRole role;
}
