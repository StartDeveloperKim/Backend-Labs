package com.study.todo.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserInfo {
    private final String email;

    public UserInfo(String email) {
        this.email = email;
    }
}
