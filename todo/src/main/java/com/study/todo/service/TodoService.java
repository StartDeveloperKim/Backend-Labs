package com.study.todo.service;

import com.study.todo.dto.UserInfo;
import com.study.todo.dto.request.TodoDoneUpdateRequest;
import com.study.todo.dto.request.TodoRemoveRequest;
import com.study.todo.dto.request.TodoRequest;
import com.study.todo.dto.request.TodoTitleUpdateRequest;
import com.study.todo.dto.response.TodoResponse;

import java.util.List;

public interface TodoService {

    Long createTodo(final TodoRequest todoRequest, final UserInfo userInfo);

    void updateTitle(final TodoTitleUpdateRequest titleUpdateRequest);

    void updateDone(final TodoDoneUpdateRequest doneUpdateRequest);

    List<TodoResponse> getTodoList(final UserInfo userInfo);

    void removeTodo(final TodoRemoveRequest todoRemoveRequest);
}
