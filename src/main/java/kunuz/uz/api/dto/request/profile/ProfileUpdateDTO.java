package kunuz.uz.api.dto.request.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import kunuz.uz.api.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileUpdateDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Minimum length of name is 3")
    private String name;

    @NotBlank(message = "surname is required")
    @Size(min = 3, max = 255, message = "Minimum length of surname is 3")
    private String surname;

    @NotBlank(message = "Username is required")
    private String username;

    @NotEmpty(message = "Profile role list is required")
    private List<ProfileRole> roleList;

}
