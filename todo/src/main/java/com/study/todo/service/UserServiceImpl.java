package com.study.todo.service;

import com.study.todo.domain.entity.User;
import com.study.todo.domain.repository.UserRepository;
import com.study.todo.dto.request.JoinRequest;
import com.study.todo.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public String userCertification(final LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email());

        if (user == null || !encoder.matches(loginRequest.password(), user.getPassword())) {
            throwIllegalArgumentException("잘못된 로그인 요청입니다.");
        }
        return user.getEmail();
    }

    @Override
    public Long join(JoinRequest joinRequest) {
        if (userRepository.existsByNickname(joinRequest.nickname()) || userRepository.existsByEmail(joinRequest.email())) {
            throwIllegalArgumentException("잘못된 회원가입 요청입니다.");
        }
        User user = User.builder()
                .email(joinRequest.email())
                .password(encoder.encode(joinRequest.password()))
                .nickname(joinRequest.nickname())
                .build();

        return userRepository.save(user).getId();
    }

    private static void throwIllegalArgumentException(String message) {
        throw new IllegalArgumentException(message);
    }
}
