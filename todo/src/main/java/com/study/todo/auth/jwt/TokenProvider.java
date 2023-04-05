package com.study.todo.auth.jwt;

import com.study.todo.domain.repository.RefreshTokenRepository;
import com.study.todo.dto.UserInfo;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
@PropertySource("classpath:application-jwt.properties")
public class TokenProvider {

    private final Key secretKey;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public TokenProvider(@Value("${jwt.secret}") String key, RefreshTokenRepository refreshTokenRepository) throws NoSuchAlgorithmException {
        byte[] keyBytes = key.getBytes();
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createAccessToken(String email) {
        long accessTokenExpireTime = 1500;
        Date expiryDate = Date.from(Instant.now().plusSeconds(accessTokenExpireTime));

        return createToken(email, expiryDate, "todo-accessToken");
    }

    public String createRefreshToken(String email) {
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return createToken(email, expiryDate, "todo-refreshToken");
    }

    private String createToken(String email, Date expiryDate, String issue) {
        return Jwts.builder()
                .signWith(secretKey)
                .setSubject(email)
                .setIssuer(issue) // 토큰을 구별하도록 임의로 이름을 지음
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public boolean validateRefreshToken(String refreshToken) {
        validateToken(refreshToken);
        return false;
    }

    public UserInfo getTokenInfo(String token) {
        Claims claims = getClaims(token);

        return new UserInfo(claims.getSubject());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build().parseClaimsJws(token).getBody();
    }
}
