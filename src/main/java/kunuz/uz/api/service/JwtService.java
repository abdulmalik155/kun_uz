package kunuz.uz.api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kunuz.uz.api.dto.request.JwtDTO;
import kunuz.uz.api.enums.ProfileRole;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JwtService {

    @Value("${security.jwt.exp-timeout}")
    private Long tokenLiveTime;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public String encode(String id, String username, List<ProfileRole> roles) {
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("username", username);
        claims.put("roles", roles);

        return Jwts
                .builder()
                .claims(claims)
                .subject(id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }


    public JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String id = claims.getSubject();
        String username = (String) claims.get("username");
        List<ProfileRole> roles = (List<ProfileRole>) claims.get("roles");
        return new JwtDTO(id, username, roles);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
