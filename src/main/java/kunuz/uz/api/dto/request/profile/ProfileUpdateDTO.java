package kunuz.uz.api.dto.request.profile;

import kunuz.uz.api.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateDTO extends ProfileRequestDTO{

    private Boolean visible;

    private ProfileStatus status;
}
