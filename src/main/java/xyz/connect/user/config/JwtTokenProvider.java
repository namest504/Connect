package xyz.connect.user.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    public final RedisTemplate<String, String> redisTemplate;


    public String getEmail(String accessToken, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken)
                .getBody().get("email", String.class);
    }

    public boolean isAccessTokenExpired(String accessToken, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken)
                .getBody().getExpiration().before(new Date());
    }

    public String createAccssToken(Long userId, String email, String key,
            Long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("sub", userId);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }


    public String createRefreshToken(Long userId, String email, String key,
            Long expireTimeMs) {

        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("sub", userId);
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs * 24 * 7)) //일주일
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        redisTemplate.opsForValue().set(
                email,
                refreshToken,
                expireTimeMs * 24 * 7,
                TimeUnit.MICROSECONDS
        );

        return refreshToken;


    }


}