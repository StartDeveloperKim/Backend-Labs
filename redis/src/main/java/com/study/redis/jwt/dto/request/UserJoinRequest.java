package com.study.redis.jwt.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserJoinRequest {

    private final String email;
    private final String password;
    private final String name;
}
