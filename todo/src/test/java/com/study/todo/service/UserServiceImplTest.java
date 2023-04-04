package com.study.todo.service;

import com.study.todo.domain.entity.User;
import com.study.todo.domain.repository.UserRepository;
import com.study.todo.dto.request.JoinRequest;
import com.study.todo.dto.request.LoginRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private final String NICKNAME = "nickname";

    @AfterEach
    void clearDB() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자가 회원가입에 성공한다.")
    void joinSuccessUser() {
        //given
        JoinRequest joinRequest = new JoinRequest(EMAIL, PASSWORD, NICKNAME);

        //when
        Long id = userService.join(joinRequest);

        //then
        User user = userRepository.findById(id).get();
        assertThat(EMAIL).isEqualTo(user.getEmail());
        assertThat(NICKNAME).isEqualTo(user.getNickname());
    }

    @Test
    @DisplayName("회원가입을 할 때 동일한 닉네임이 있다면 예외를 던진다.")
    void joinWithDuplicateNickname() {
        //given
        saveUser();
        //when
        JoinRequest joinRequest = new JoinRequest(EMAIL, PASSWORD, NICKNAME);

        //then
        assertThrows(IllegalArgumentException.class, () -> userService.join(joinRequest));
    }

    @Test
    @DisplayName("로그인 요청에 성공한다.")
    void certificateSuccess() {
        //given
        saveUser();
        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);
        //when
        String email = userService.userCertification(loginRequest);

        //then
        assertThat(EMAIL).isEqualTo(email);
    }

    @Test
    @DisplayName("로그인 요청시 다른 비밀번호를 보내면 예외를 던진다.")
    void certificateByIllegalPassword() {
        //given
        saveUser();
        String illegalPassword = "illegalPassword";
        LoginRequest loginRequest = new LoginRequest(EMAIL, illegalPassword);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> userService.userCertification(loginRequest));
    }

    private void saveUser() {
        User user = User.builder()
                .nickname(NICKNAME)
                .password(encoder.encode(PASSWORD))
                .email(EMAIL).build();
        userRepository.save(user);
    }
}