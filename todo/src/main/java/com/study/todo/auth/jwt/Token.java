package com.study.todo.auth.jwt;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Token {
    private final String accessToken;

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }
}
