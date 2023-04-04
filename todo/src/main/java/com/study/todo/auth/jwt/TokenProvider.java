package com.study.todo.auth.jwt;

import com.study.todo.domain.entity.User;
import com.study.todo.dto.UserInfo;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class TokenProvider {

    private final Key secretKey;

    @Autowired
    public TokenProvider(@Value("SECRET_KEY") String key) throws NoSuchAlgorithmException {
        byte[] keyBytes = key.getBytes();
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
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

    public UserInfo validateAccessToken(String accessToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build().parseClaimsJws(accessToken).getBody();

        return new UserInfo(claims.getSubject());
    }
}
