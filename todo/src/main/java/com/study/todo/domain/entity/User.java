package com.study.todo.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "USERS")
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    private String password;

    private String nickname;

    private String authProvider;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private List<Todo> todoList = new ArrayList<>();

    @Builder
    public User(String email, String password, String nickname, String authProvider) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authProvider = authProvider;
    }
}
