package com.study.redis.jwt.jwt;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class RefreshTokenRepository {

    private final RedisTemplate redisTemplate;

    public RefreshTokenRepository(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(final RefreshToken refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getRefreshToken(), refreshToken.getEmail());
        redisTemplate.expire(refreshToken.getRefreshToken(), 60L, TimeUnit.SECONDS);
    }

    public Optional<RefreshToken> findByEmail(final String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String email = valueOperations.get(refreshToken);

        if (email == null) {
            return Optional.empty();
        }
        return Optional.of(new RefreshToken(refreshToken, email));
    }
}
