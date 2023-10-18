package com.lim1t.springcloudgateway.route;

import com.lim1t.springcloudgateway.filter.EurekaClientValidationFilter;
import com.lim1t.springcloudgateway.filter.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfig {

    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    private final EurekaClientValidationFilter eurekaClientValidationFilter;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public GatewayRoutingConfig(JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter,
            EurekaClientValidationFilter eurekaClientValidationFilter) {
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
        this.eurekaClientValidationFilter = eurekaClientValidationFilter;
    }

    @Bean
    public RouteLocator Routes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("post_route", r -> r
                        .path("/api/*/post/**")
                        .filters(f -> f.filter(jwtTokenAuthenticationFilter.apply(
                                new JwtTokenAuthenticationFilter.Config(secretKey))))
                        .uri("lb://post-service"))

                .route("user_route", r -> r
                        .path("/api/*/user/**")
                        .uri("lb://user-service"))

                .route("user_route", r -> r
                        .path("/api/*/user/confirm/auth-mail")
                        .filters(f -> f.filters(eurekaClientValidationFilter.apply(
                                new EurekaClientValidationFilter.Config())))
                        .uri("lb://user-service"))

                .route("mail_route", r -> r
                        .path("/api/*/mail/**")
                        .uri("lb://mail-service"))

                .build();
    }

}
