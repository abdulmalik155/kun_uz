package kunuz.uz.api.dto.request;

import kunuz.uz.api.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtDTO {
    private String id;
    private String username;
    private List<ProfileRole> role;
}
