package com.study.todo.controller;

import com.study.todo.dto.UserInfo;
import com.study.todo.dto.response.TodoResponse;
import com.study.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/todo")
@RestController
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getTodoList(@LoginUser UserInfo userInfo) {
        List<TodoResponse> todoList = todoService.getTodoList(userInfo);
        return ResponseEntity.ok(todoList);
    }
}
