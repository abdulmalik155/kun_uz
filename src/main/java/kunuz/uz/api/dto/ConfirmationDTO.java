package kunuz.uz.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmationDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank(message = "Verification code required")
    private String message;
}
