package com.study.production.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ArticleResponse {

    private final String title;
    private final String content;
    private final String createAt;

    public ArticleResponse(String title, String content, LocalDateTime createAt) {
        this.title = title;
        this.content = content;
        this.createAt = createAt.format(DateTimeFormatter.ISO_DATE);
    }
}
