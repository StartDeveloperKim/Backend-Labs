package com.study.todo.controller;

import com.study.todo.auth.LoginUser;
import com.study.todo.dto.UserInfo;
import com.study.todo.dto.request.TodoDoneUpdateRequest;
import com.study.todo.dto.request.TodoRemoveRequest;
import com.study.todo.dto.request.TodoRequest;
import com.study.todo.dto.request.TodoTitleUpdateRequest;
import com.study.todo.dto.response.TodoResponse;
import com.study.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Long> addTodo(@LoginUser UserInfo userInfo,
                                        @RequestBody TodoRequest todoRequest) {
        Long todoId = todoService.createTodo(todoRequest, userInfo);
        return ResponseEntity.ok(todoId);
    }

    @PatchMapping("/title")
    public ResponseEntity<?> updateTitle(@RequestBody TodoTitleUpdateRequest titleUpdateRequest) {
        todoService.updateTitle(titleUpdateRequest);
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/done")
    public ResponseEntity<?> updateDone(@RequestBody TodoDoneUpdateRequest doneUpdateRequest) {
        todoService.updateDone(doneUpdateRequest);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping
    public ResponseEntity<?> removeTodo(@RequestBody TodoRemoveRequest todoRemoveRequest) {
        todoService.removeTodo(todoRemoveRequest);
        return ResponseEntity.ok(null);
    }
}
