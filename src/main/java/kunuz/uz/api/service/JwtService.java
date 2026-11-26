package kunuz.uz.api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kunuz.uz.api.dto.request.JwtDTO;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.enums.TokenType;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JwtService {

    private static final Long accessTokenLiveTime = 86400000L;
    private static final Long refreshTokenLiveTime = 86400000L * 30;

    private static final String secretKey = "veryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";

    private static String encode(String id, String username, List<ProfileRole> roles, String tokenType, Long liveTime) {
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("username", username);
        claims.put("roles", roles);
        claims.put("type", tokenType);

        return Jwts
                .builder()
                .claims(claims)
                .subject(id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + liveTime))
                .signWith(getSignInKey())
                .compact();
    }


    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Long id = Long.valueOf(claims.getSubject());
        String username = (String) claims.get("username");
        List<ProfileRole> roles = (List<ProfileRole>) claims.get("roles");
        TokenType tokenType =TokenType.valueOf(claims.get("type", String.class));
        return new JwtDTO(id, username, roles, tokenType);
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String accessToken(Long id, String username, List<ProfileRole> roleList) {
        return encode(id.toString(), username, roleList, TokenType.ACCESS.name(), accessTokenLiveTime);

    }

    public static String refreshToken(Long id, String username, List<ProfileRole> roleList) {
        return encode(id.toString(), username, roleList, TokenType.REFRESH.name(), refreshTokenLiveTime);
    }
}
