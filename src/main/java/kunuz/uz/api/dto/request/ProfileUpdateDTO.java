package kunuz.uz.api.dto.request;

import kunuz.uz.api.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateDTO extends ProfileRequestDTO {


    private ProfileStatus status;
}
