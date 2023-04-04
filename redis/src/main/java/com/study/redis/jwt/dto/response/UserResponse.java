package com.study.redis.jwt.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserResponse {

    private final String email;
    private final String name;
}
