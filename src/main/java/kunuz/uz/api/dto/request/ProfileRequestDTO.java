package kunuz.uz.api.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kunuz.uz.api.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Minimum length of name is 3")
    private String name;

    @NotBlank(message = "surname is required")
    @Size(min = 3, max = 255, message = "Minimum length of surname is 3")
    private String surname;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "minimum length of password is 8 characters")
    private String password;

    @NotEmpty(message = "Profile role list is required")
    private List<ProfileRole> roleList;

}
