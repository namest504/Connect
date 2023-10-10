package com.lim1t.springcloudgateway.route;

import com.lim1t.springcloudgateway.config.JwtFilterConfig;
import com.lim1t.springcloudgateway.filter.JwtTokenAuthenticationFilter;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfig {

    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    private final Key secretKey;

    public GatewayRoutingConfig(JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter,
            @Value("${jwt.secret.key}") String secretKey) {
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    public RouteLocator Routes(RouteLocatorBuilder builder) {

        JwtFilterConfig config = new JwtFilterConfig();
        config.setSecretKey(secretKey);

        return builder.routes()
                .route("post_route", r -> r.path("/api/*/post/**")
                        .filters(f -> f.filter(jwtTokenAuthenticationFilter.apply(config)))
                        .uri("lb://post-service"))
                .route("user_route", r -> r.path("/api/*/user/**")
                        .uri("lb://user-service"))
                .build();
    }

}
