package com.study.redis.jwt.controller;

import com.study.redis.jwt.dto.request.UserJoinRequest;
import com.study.redis.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> joinUser(@RequestBody final UserJoinRequest userJoinRequest) {
        Long userId = userService.joinUser(userJoinRequest);
        return ResponseEntity.created(URI.create("/user/" + userId)).build();
    }
}
