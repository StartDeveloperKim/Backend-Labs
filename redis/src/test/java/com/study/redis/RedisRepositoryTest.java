package com.study.redis;

import com.study.redis.person.Person;
import com.study.redis.person.PersonRedisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisRepositoryTest {

    @Autowired
    private PersonRedisRepository personRedisRepository;

    @Test
    @DisplayName("레디스에서 값을 저장한 후 읽어온다.")
    void redisReadTest() {
        //given
        Person person = new Person("John", 20, LocalDateTime.now());
        Person savedPerson = personRedisRepository.save(person);

        //when
        Person findPerson = personRedisRepository.findById(savedPerson.getId()).get();

        //then
        assertThat(person.getName()).isEqualTo(findPerson.getName());
        assertThat(person.getAge()).isEqualTo(findPerson.getAge());
        assertThat(savedPerson.getId()).isEqualTo(findPerson.getId());
    }
}
