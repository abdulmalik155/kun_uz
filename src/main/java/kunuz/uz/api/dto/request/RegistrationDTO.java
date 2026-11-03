package kunuz.uz.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO { // TODO write validation
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Surname required")
    private String surname;
    @Email
    @NotBlank(message = "Username required")
    private String username;
    @NotBlank(message = "password required")
    private String password;
}
