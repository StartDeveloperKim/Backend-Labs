package com.study.redis.jwt.jwt.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccessTokenRequest {
    private final String accessToken;
}
