package com.study.todo.service;

import com.study.todo.domain.entity.Todo;
import com.study.todo.domain.entity.User;
import com.study.todo.domain.repository.TodoRepository;
import com.study.todo.domain.repository.UserRepository;
import com.study.todo.dto.request.TodoDoneUpdateRequest;
import com.study.todo.dto.UserInfo;
import com.study.todo.dto.request.TodoRemoveRequest;
import com.study.todo.dto.request.TodoRequest;
import com.study.todo.dto.request.TodoTitleUpdateRequest;
import com.study.todo.dto.response.TodoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Override
    public Long createTodo(final TodoRequest todoRequest, final UserInfo userInfo) {
        User user = userRepository.findByEmail(userInfo.email());
        Todo todo = Todo.builder()
                .title(todoRequest.title()).user(user).done(false)
                .build();
        return todoRepository.save(todo).getId();
    }

    @Override
    public void updateTitle(final TodoTitleUpdateRequest titleUpdateRequest) {
        Todo todo = findTodo(titleUpdateRequest.id());
        todo.updateTitle(titleUpdateRequest.title());
    }

    @Override
    public void updateDone(final TodoDoneUpdateRequest doneUpdateRequest) {
        Todo todo = findTodo(doneUpdateRequest.id());
        todo.updateDone(doneUpdateRequest.done());
    }

    @Override
    public void removeTodo(final TodoRemoveRequest todoRemoveRequest) {
        todoRepository.delete(findTodo(todoRemoveRequest.id()));
    }

    private Todo findTodo(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<TodoResponse> getTodoList(final UserInfo userInfo) {
        User user = userRepository.findByEmail(userInfo.email());
        List<Todo> todoList = todoRepository.findByUserId(user.getId());
        return todoList.stream()
                .map(todo -> new TodoResponse(todo.getTitle(), todo.isDone()))
                .collect(Collectors.toList());
    }
}
