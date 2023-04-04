package com.study.redis.person;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@ToString
@Getter
@RedisHash(value = "people", timeToLive = 600)
// value : Redis의 keyspace값으로 사용된다.
// timeToLive : 만료시간을 seconds 단위로 설정할 수 있다. 기본값은 만료시간이 없는 -1L이다.
public class Person {

    @Id
    private String id;
    // Redis Key 값이 되면 null로 세팅하면 랜덤값이 설정된다.
    // keyspace와 합쳐져서 레디스에 저장된 최종 키 값은 keyspace:id 가 된다.
    private final String name;
    private final Integer age;
    private final LocalDateTime createAt;

    public Person(String name, Integer age, LocalDateTime createAt) {
        this.name = name;
        this.age = age;
        this.createAt = createAt;
    }
}
