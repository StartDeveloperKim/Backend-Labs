package com.study.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName("Redis Value로 String타입을 저장한다.")
    void redisTemplateStringTest() {
        //given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "key";

        //when
        valueOperations.set(key, "안녕하세요");

        //then
        String value = valueOperations.get(key);
        Assertions.assertThat(value).isEqualTo("안녕하세요");

    }
}
