package com.study.todo.auth.jwt;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Token {
    private final String accessToken;
    private final String refreshToken;

    public Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
