package com.study.todo.dto.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class JoinRequest {
    private final String email;
    private final String password;
    private final String nickname;

    public JoinRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
