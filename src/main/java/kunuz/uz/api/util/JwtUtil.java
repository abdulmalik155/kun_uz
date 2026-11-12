package kunuz.uz.api.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import kunuz.uz.api.dto.request.JwtDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.exceptions.AppBadException;
import kunuz.uz.api.exceptions.UnauthorizedProfileException;
import kunuz.uz.api.service.JwtService;
import kunuz.uz.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Autowired
    private JwtService jwtService;


    @Autowired
    private ProfileService profileService;



    public JwtDTO profile(String jwt){
        try {
            String token = jwt.substring(7);
            JwtDTO dto = jwtService.decode(token);
            return dto;
        } catch (MalformedJwtException e) {
            throw new AppBadException("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            throw new AppBadException("JWT token is expired.");
        } catch (UnsupportedJwtException e) {
            throw new AppBadException("JWT token is unsupported.");
        } catch (IllegalArgumentException e) {
            throw new AppBadException("JWT claims string is empty.");
        } catch (JwtException e) {
            throw new AppBadException("Jwt validation error.");
        }
    }

    public Long profileId(String token) {
        return Long.valueOf(profile(token).getId());
    }

    public Long moderatorId(String token) {
        Long id = Long.valueOf(profile(token).getId());
        checkProfileRole(id, ProfileRole.ROLE_MODERATOR);
        return id;
    }

    private void checkProfileRole(Long moderatorId, ProfileRole role){
        ProfileResponseDTO profileDTO = profileService.getById(moderatorId);
        if(!profileDTO.getRoleList().contains(role)){
            throw new UnauthorizedProfileException("Unauthorized profile");
        }
    }

    public Long publisherId(String token) {
        Long id = Long.valueOf(profile(token).getId());
        checkProfileRole(id, ProfileRole.ROLE_PUBLISHER);
        return id;
    }

    public Long adminId(String token) {
        Long id = Long.valueOf(profile(token).getId());
        checkProfileRole(id, ProfileRole.ROLE_ADMIN);
        return id;
    }
}
