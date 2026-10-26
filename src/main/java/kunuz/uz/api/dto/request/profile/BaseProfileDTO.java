package kunuz.uz.api.dto.request.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseProfileDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Minimum length of name is 3")
    private String name;

    @Size(min = 3, max = 255, message = "Minimum length of surname is 3")
    private String surname;

    @Email(message = "Invalid email")
    private String email;

    @Pattern(
            regexp = "^[0-9]{9}$",
            message = "Phone number must be exactly 9 digits without +998"
    )
    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "minimum length of password is 8 characters")
    private String password;
}
