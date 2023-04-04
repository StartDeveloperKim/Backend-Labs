package com.study.todo.domain.repository;

import com.study.todo.domain.entity.Todo;
import com.study.todo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("select todo from Todo todo where todo.user.id=:userId order by todo.crateAt desc")
    List<Todo> findByUserId(Long userId);
}
