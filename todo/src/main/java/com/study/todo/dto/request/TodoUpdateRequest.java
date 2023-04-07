package com.study.todo.dto.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TodoUpdateRequest {
    private final Long id;
    private final String title;
    private final boolean done;
    public TodoUpdateRequest(Long id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }
}
