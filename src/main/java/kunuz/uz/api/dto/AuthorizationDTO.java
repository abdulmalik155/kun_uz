package kunuz.uz.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationDTO {

    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "password required")
    private String password;
}
