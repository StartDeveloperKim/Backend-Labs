package com.study.redis.jwt.jwt.dto;

public record RefreshTokenRequest(String email, String password) {
}
