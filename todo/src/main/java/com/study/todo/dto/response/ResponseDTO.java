package com.study.todo.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ResponseDTO<T> {
    private final List<T> data;
}
