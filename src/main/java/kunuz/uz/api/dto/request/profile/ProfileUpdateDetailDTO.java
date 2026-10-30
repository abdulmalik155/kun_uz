package kunuz.uz.api.dto.request.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateDetailDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Minimum length of name is 3")
    private String name;

    @NotBlank(message = "surname is required")
    @Size(min = 3, max = 255, message = "Minimum length of surname is 3")
    private String surname;
}
