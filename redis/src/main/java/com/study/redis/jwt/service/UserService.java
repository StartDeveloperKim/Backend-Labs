package com.study.redis.jwt.service;

import com.study.redis.jwt.domain.User;
import com.study.redis.jwt.dto.request.UserJoinRequest;
import com.study.redis.jwt.dto.response.UserResponse;
import com.study.redis.jwt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long joinUser(final UserJoinRequest userJoinRequest) {
        User user = User.builder()
                .email(userJoinRequest.getEmail())
                .password(userJoinRequest.getPassword()) // 암호화 해야하지만 공부프로젝트이므로 평문으로 저장
                .name(userJoinRequest.getName()).build();

        return userRepository.save(user).getId();
    }

    public UserResponse getUserByEmail(final String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        return new UserResponse(user.getEmail(), user.getName());
    }
}
