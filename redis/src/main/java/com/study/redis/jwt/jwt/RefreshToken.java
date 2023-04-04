package com.study.redis.jwt.jwt;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 60)
public class RefreshToken {

    @Id
    private final String refreshToken;
    private final Long id;

    public RefreshToken(final String refreshToken, final Long id) {
        this.refreshToken = refreshToken;
        this.id = id;
    }
}
