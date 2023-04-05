package com.study.todo.controller;

import com.study.todo.auth.jwt.Token;
import com.study.todo.auth.jwt.TokenProvider;
import com.study.todo.dto.request.JoinRequest;
import com.study.todo.dto.request.LoginRequest;
import com.study.todo.dto.response.LoginResponse;
import com.study.todo.dto.response.joinResponse;
import com.study.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
@RestController
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            String email = userService.userCertification(loginRequest);
            LoginResponse loginResponse = new LoginResponse(tokenProvider.createAccessToken(email), tokenProvider.createRefreshToken(email));

            return ResponseEntity.ok(loginResponse);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<joinResponse> join(@RequestBody JoinRequest joinRequest) {
        try {
            Long userId = userService.join(joinRequest);
            String email = joinRequest.getEmail();
            Token token = new Token(tokenProvider.createAccessToken(email), tokenProvider.createRefreshToken(email));

            return ResponseEntity.ok(new joinResponse(true, token,"회원가입에 성공했습니다."));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new joinResponse(false, null, "잘못된 회원가입 요청입니다."));
        }

    }

}
