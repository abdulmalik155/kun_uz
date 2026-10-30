package kunuz.uz.api.dto.request.profile;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDTO extends ProfileUpdateDTO {

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "minimum length of password is 8 characters")
    private String password;

}
