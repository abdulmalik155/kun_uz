package kunuz.uz.api.dto.response;

import kunuz.uz.api.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthResponseDTO {
    private String name;
    private String surname;
    private String username;
    private List<ProfileRole> roleList;
    private String accessToken;
    private String refreshToken;
}
