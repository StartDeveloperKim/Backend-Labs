package com.study.todo.service;

import com.study.todo.domain.entity.Todo;
import com.study.todo.domain.entity.User;
import com.study.todo.domain.repository.TodoRepository;
import com.study.todo.domain.repository.UserRepository;
import com.study.todo.dto.UserInfo;
import com.study.todo.dto.request.TodoDoneUpdateRequest;
import com.study.todo.dto.request.TodoRemoveRequest;
import com.study.todo.dto.request.TodoRequest;
import com.study.todo.dto.request.TodoTitleUpdateRequest;
import com.study.todo.dto.response.TodoResponse;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TodoServiceImplTest {

    @Autowired
    private TodoService todoService;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    private final String EMAIL = "kkk@gmail.com";
    private final String TITLE = "테스트1";

    @BeforeEach
    void addUser() {
        User user = User.builder().email(EMAIL).password("1234").authProvider(null).build();
        userRepository.save(user);
    }

    @AfterEach
    void clearDB() {
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("TODO 하나가 추가되어야 한다.")
    void createTodo() {
        //given
        UserInfo userInfo = new UserInfo(EMAIL);
        TodoRequest todoRequest = new TodoRequest(TITLE);

        //when
        Long id = todoService.createTodo(todoRequest, userInfo);
        Todo todo = getTodo(id);

        //then
        assertThat("테스트1").isEqualTo(todo.getTitle());
    }

    @Test
    @DisplayName("제목이 수정되야한다.")
    void updateTitle() {
        //given
        Todo savedTodo = taskBeforeUpdateService();
        TodoTitleUpdateRequest titleUpdateRequest = new TodoTitleUpdateRequest(savedTodo.getId(), "테스트2");
        //when
        todoService.updateTitle(titleUpdateRequest);

        //then
        Todo updateTodo = getTodo(savedTodo.getId());
        assertThat(titleUpdateRequest.title()).isEqualTo(updateTodo.getTitle());
    }

    @Test
    @DisplayName("완료여부가 수정되야한다.")
    void updateDone() {
        //given
        Todo savedTodo = taskBeforeUpdateService();
        TodoDoneUpdateRequest doneUpdateRequest = new TodoDoneUpdateRequest(savedTodo.getId(), true);

        //when
        todoService.updateDone(doneUpdateRequest);

        //then
        Todo updateTodo = getTodo(savedTodo.getId());
        assertThat(doneUpdateRequest.done()).isEqualTo(updateTodo.isDone());
    }

    @Test
    @DisplayName("TODO 하나가 삭젝되어야 한다.")
    void removeTodo() {
        //given
        Todo savedTodo = taskBeforeUpdateService();
        //when
        todoService.removeTodo(new TodoRemoveRequest(savedTodo.getId()));
        //then
        Assertions.assertThrows(EntityNotFoundException.class, () -> getTodo(savedTodo.getId()));
    }

    @Test
    @DisplayName("User를 통해 저장된 TODO-List를 가져온다.")
    void getTodoList() {
        //given
        User user = userRepository.findByEmail(EMAIL);
        Todo todo1 = Todo.builder().title("테스트1").done(false).user(user).build();
        Todo todo2 = Todo.builder().title("테스트2").done(false).user(user).build();
        Todo todo3 = Todo.builder().title("테스트3").done(false).user(user).build();

        Todo savedTodo1 = todoRepository.save(todo1);
        Todo savedTodo2 = todoRepository.save(todo2);
        Todo savedTodo3 = todoRepository.save(todo3);
        //when
        List<TodoResponse> todoList = todoService.getTodoList(new UserInfo(EMAIL));

        //then
        assertThat(savedTodo3.getTitle()).isEqualTo(todoList.get(2).title());
        assertThat(savedTodo2.getTitle()).isEqualTo(todoList.get(1).title());
        assertThat(savedTodo1.getTitle()).isEqualTo(todoList.get(0).title());
    }

    private Todo getTodo(Long id) {
        return todoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private Todo taskBeforeUpdateService() {
        User user = userRepository.findByEmail(EMAIL);
        Todo todo = Todo.builder().title(TITLE).done(false).user(user).build();
        return todoRepository.save(todo);
    }
}