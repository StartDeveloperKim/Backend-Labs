package com.study.todo.controller;

import com.study.todo.auth.jwt.Token;
import com.study.todo.auth.jwt.TokenProvider;
import com.study.todo.dto.request.JoinRequest;
import com.study.todo.dto.request.LoginRequest;
import com.study.todo.dto.response.JoinResponse;
import com.study.todo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
@RestController
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<Token> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            String email = userService.userCertification(loginRequest);

            String accessToken = tokenProvider.createAccessToken(email);
            String refreshToken = tokenProvider.createRefreshToken(email);

            Cookie refreshTokenCookie = new Cookie("RefreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            response.addCookie(refreshTokenCookie);

            Token token = new Token(accessToken);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest joinRequest) {
        try {
            userService.join(joinRequest);

            return ResponseEntity.ok(new JoinResponse(true,"회원가입에 성공했습니다."));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new JoinResponse(false, "잘못된 회원가입 요청입니다."));
        }

    }

}
