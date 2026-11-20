package kunuz.uz.api.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import kunuz.uz.api.dto.custom.CustomUserDetails;
import kunuz.uz.api.dto.request.JwtDTO;
import kunuz.uz.api.exceptions.AppBadException;
import kunuz.uz.api.service.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static JwtDTO profile(String jwt){
        try {
            String token = jwt.substring(7);
            JwtDTO dto = JwtService.decode(token);
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

    public static CustomUserDetails currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user;
    }

    public static Long currentUserId(){
        return currentUser().getId();
    }

    public static Long profileId(String token) {
        return profile(token).getId();
    }
}
