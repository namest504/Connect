package com.lim1t.email.domain.mail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "mail")
public class Mail {

    @Id
    private String id;

    private String email;

    @TimeToLive
    private long expiredTime;

    @Builder
    private Mail(final String id,
                 final String email,
                 final long expiredTime) {
        this.id = id;
        this.email = email;
        this.expiredTime = expiredTime;
    }
}
