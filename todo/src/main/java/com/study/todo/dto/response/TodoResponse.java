package com.study.todo.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class TodoResponse {
    private final String title;
    private final boolean done;
    private final String createAt;

    public TodoResponse(String title, boolean done, LocalDateTime createAt) {
        this.title = title;
        this.done = done;
        this.createAt = createAt.format(DateTimeFormatter.ISO_DATE);
    }
}
