package com.study.todo.dto.response;

import com.study.todo.auth.jwt.Token;

public record joinResponse(boolean isJoinSuccess, Token token, String message) {
}
