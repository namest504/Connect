package com.lim1t.springcloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfig {

    @Bean
    public RouteLocator Routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("post_route", r -> r.path("/api/*/post/**")
                        .uri("lb://post-service"))
                .route("user_route", r -> r.path("/api/*/user/**")
                        .uri("lb://user-service"))
                .build();
    }

}
