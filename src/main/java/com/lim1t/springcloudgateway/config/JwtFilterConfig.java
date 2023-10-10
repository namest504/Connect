package com.lim1t.springcloudgateway.config;

import java.security.Key;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtFilterConfig {

    private Key secretKey;
}

