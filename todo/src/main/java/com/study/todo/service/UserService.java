package com.study.todo.service;

import com.study.todo.dto.request.JoinRequest;
import com.study.todo.dto.request.LoginRequest;

public interface UserService {

    String userCertification(LoginRequest loginRequest);

    Long join(JoinRequest joinRequest);
}
