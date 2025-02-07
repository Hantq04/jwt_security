package ra.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ra.security.CustomUserDetails;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtTokenProvider {
    @Value("${ra.jwt.secret}")
    String JWT_SECRET;

    @Value("${ra.jwt.expiration}")
    Integer JWT_EXPIRATION;

    public String generateToken(CustomUserDetails customUserDetails) {
        Date now = new Date();
        Date dateExpire = new Date(now.getTime() + JWT_EXPIRATION);
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(dateExpire)
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String getUserNameFromJwt(String token) {
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        }catch (MalformedJwtException e) {
            log.error("INVALID_JWT_TOKEN.");
        }catch (ExpiredJwtException e) {
            log.error("EXPIRED_JWT_TOKEN.");
        }catch (UnsupportedJwtException e) {
            log.error("UNSUPPORTED_JWT_TOKEN.");
        }catch (IllegalArgumentException e) {
            log.error("JWT_CLAIMS_STRING_IS_EMPTY.");
        }
        return false;
    }
}
