package com.study.todo.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class TodoResponse {
    private final Long id;
    private final String title;
    private final boolean done;
    private final String createAt;

    public TodoResponse(Long id, String title, boolean done, LocalDateTime createAt) {
        this.id = id;
        this.title = title;
        this.done = done;
        this.createAt = createAt.format(DateTimeFormatter.ISO_DATE);
    }
}
