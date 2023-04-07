package com.study.todo.controller;

import com.study.todo.auth.LoginUser;
import com.study.todo.dto.UserInfo;
import com.study.todo.dto.request.TodoRemoveRequest;
import com.study.todo.dto.request.TodoRequest;
import com.study.todo.dto.request.TodoUpdateRequest;
import com.study.todo.dto.response.ResponseDTO;
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
    public ResponseEntity<ResponseDTO<TodoResponse>> getTodoList(@LoginUser UserInfo userInfo) {
        ResponseDTO<TodoResponse> todoResponseResponseDTO = getTodoResponseResponseDTO(userInfo);
        return ResponseEntity.ok(todoResponseResponseDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<TodoResponse>> addTodo(@LoginUser UserInfo userInfo,
                                        @RequestBody TodoRequest todoRequest) {
        todoService.createTodo(todoRequest, userInfo);
        ResponseDTO<TodoResponse> todoResponseResponseDTO = getTodoResponseResponseDTO(userInfo);
        return ResponseEntity.ok(todoResponseResponseDTO);
    }

    @PatchMapping
    public ResponseEntity<ResponseDTO<TodoResponse>> updateTitle(@LoginUser UserInfo userInfo,
                                                                 @RequestBody TodoUpdateRequest titleUpdateRequest) {
        todoService.updateTodo(titleUpdateRequest);
        ResponseDTO<TodoResponse> todoResponseResponseDTO = getTodoResponseResponseDTO(userInfo);
        return ResponseEntity.ok(todoResponseResponseDTO);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<TodoResponse>> removeTodo(@LoginUser UserInfo userInfo,
                                        @RequestBody TodoRemoveRequest todoRemoveRequest) {
        todoService.removeTodo(todoRemoveRequest);
        ResponseDTO<TodoResponse> todoResponseResponseDTO = getTodoResponseResponseDTO(userInfo);
        return ResponseEntity.ok(todoResponseResponseDTO);
    }

    private ResponseDTO<TodoResponse> getTodoResponseResponseDTO(UserInfo userInfo) {
        List<TodoResponse> todoList = todoService.getTodoList(userInfo);
        return new ResponseDTO<>(todoList);
    }
}
