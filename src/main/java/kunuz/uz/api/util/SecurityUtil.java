package kunuz.uz.api.util;

import io.jsonwebtoken.JwtException;
import kunuz.uz.api.dto.custom.CustomUserDetails;
import kunuz.uz.api.dto.request.JwtDTO;
import kunuz.uz.api.exceptions.InvalidJwtException;
import kunuz.uz.api.service.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static JwtDTO profile(String jwt) {
        try {
            String token = jwt.substring(7);
            JwtDTO dto = JwtService.decode(token);
            return dto;
        } catch (JwtException e) {
            throw new InvalidJwtException("Something went wrong while decoding jwt " + e.getMessage());
        }
    }

    public static CustomUserDetails currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user;
    }

    public static Long currentUserId() {
        return currentUser().getId();
    }

    public static Long profileId(String token) {
        return profile(token).getId();
    }
}
