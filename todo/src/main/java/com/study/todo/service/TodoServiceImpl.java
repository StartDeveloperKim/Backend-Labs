package com.study.todo.service;

import com.study.todo.domain.entity.Todo;
import com.study.todo.domain.entity.User;
import com.study.todo.domain.repository.TodoRepository;
import com.study.todo.domain.repository.UserRepository;
import com.study.todo.dto.request.TodoDoneUpdateRequest;
import com.study.todo.dto.UserInfo;
import com.study.todo.dto.request.TodoRemoveRequest;
import com.study.todo.dto.request.TodoRequest;
import com.study.todo.dto.request.TodoUpdateRequest;
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
        User user = userRepository.findByEmail(userInfo.getEmail());
        Todo todo = Todo.builder()
                .title(todoRequest.title()).user(user).done(false)
                .build();
        return todoRepository.save(todo).getId();
    }

    @Override
    public void updateTodo(TodoUpdateRequest todoUpdateRequest) {
        Todo todo = findTodo(todoUpdateRequest.getId());
        todo.update(todoUpdateRequest.getTitle(), todoUpdateRequest.isDone());
    }

    @Override
    public void removeTodo(final TodoRemoveRequest todoRemoveRequest) {
        todoRepository.delete(findTodo(todoRemoveRequest.getId()));
    }

    private Todo findTodo(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<TodoResponse> getTodoList(final UserInfo userInfo) {
        User user = userRepository.findByEmail(userInfo.getEmail());
        List<Todo> todoList = todoRepository.findByUserId(user.getId());
        return todoList.stream()
                .map(todo -> new TodoResponse(todo.getId(), todo.getTitle(), todo.isDone(), todo.getCrateAt()))
                .collect(Collectors.toList());
    }
}
