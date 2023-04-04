package com.study.redis.jwt.jwt;

import com.study.redis.jwt.jwt.dto.AccessTokenRequest;
import com.study.redis.jwt.jwt.dto.AccessTokenResponse;
import com.study.redis.jwt.jwt.dto.RefreshTokenRequest;
import com.study.redis.jwt.jwt.dto.RefreshTokenResponse;
import com.study.redis.jwt.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TokenService {

    // 원래 설정 파일에서 따로 관리해야하지만 공부목적 프로젝트이므로 이렇게 함
    private final String SECRET_KEY = "zmzmzmzalalalaqpqpqpwowoeijdksjdka1231431535";
    private final int EXPIRE_TIME = 20000;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SecretKey secretKey;

    public TokenService(final UserRepository userRepository, final RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public RefreshTokenResponse createRefreshToken(final RefreshTokenRequest request) throws AuthenticationException {
        Long userId = userRepository.findByEmail(request.email()).orElseThrow(AuthenticationException::new).getId();

        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), userId);
        refreshTokenRepository.save(refreshToken);

        return new RefreshTokenResponse(refreshToken);
    }

    public AccessTokenResponse createAccessToken(final AccessTokenRequest request) {

        LocalDateTime now = LocalDateTime.now();

        return null;
    }

    public String extractUserEmail(final String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            throw new RuntimeException();
        }
    }

    public String parseAccessToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
