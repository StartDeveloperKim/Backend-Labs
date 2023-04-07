package com.study.todo.service;

import com.study.todo.dto.UserInfo;
import com.study.todo.dto.request.TodoDoneUpdateRequest;
import com.study.todo.dto.request.TodoRemoveRequest;
import com.study.todo.dto.request.TodoRequest;
import com.study.todo.dto.request.TodoUpdateRequest;
import com.study.todo.dto.response.TodoResponse;

import java.util.List;

public interface TodoService {

    Long createTodo(final TodoRequest todoRequest, final UserInfo userInfo);

    void updateTodo(final TodoUpdateRequest todoUpdateRequest);

    List<TodoResponse> getTodoList(final UserInfo userInfo);

    void removeTodo(final TodoRemoveRequest todoRemoveRequest);
}
