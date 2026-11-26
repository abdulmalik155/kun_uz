package kunuz.uz.api.dto.request;

import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtDTO {
    private Long id;
    private String username;
    private List<ProfileRole> role;
    private TokenType tokenType;
}
