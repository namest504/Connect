package com.lim1t.email.global.util;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class RandomNumberGenerator {

    public String createUUID() {
        return UUID.randomUUID()
                .toString();
    }
}
