package kunuz.uz.api.dto.request;


import jakarta.validation.constraints.*;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDTO {

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

    @NotNull(message = "Profile status is required")
    private ProfileStatus status;

    @NotNull(message = "Profile role is required")
    private ProfileRole role;



}
