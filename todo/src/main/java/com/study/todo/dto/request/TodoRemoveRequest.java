package com.study.todo.dto.request;

import lombok.Getter;

@Getter
public class TodoRemoveRequest {
    private final Long id;
    public TodoRemoveRequest(Long id) {
        this.id = id;
    }
}
