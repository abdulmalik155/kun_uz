package kunuz.uz.api.dto.request.profile;


import jakarta.validation.constraints.NotNull;
import kunuz.uz.api.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDTO extends BaseProfileDTO {

    @NotNull(message = "Profile role is required")
    private ProfileRole role;

    private Boolean visible;
}
