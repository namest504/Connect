package xyz.connect.post.config;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import xyz.connect.post.filter.AclFilter;
import xyz.connect.post.filter.AuthFilter;
import xyz.connect.post.filter.FilterExceptionHandler;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final AclFilter aclFilter;
    private final FilterExceptionHandler filterExceptionHandler;
    private final AuthFilter authFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin().disable()
                .logout().disable()

                .headers().frameOptions().sameOrigin()
                .and()

                .cors().configurationSource(corsConfigSource())
                .and()

                .csrf().disable()

                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()

                .addFilterBefore(filterExceptionHandler, DisableEncodeUrlFilter.class)
                .addFilterAfter(aclFilter, FilterExceptionHandler.class)
                .addFilterAfter(authFilter, AclFilter.class)
                .build();
    }

    private UrlBasedCorsConfigurationSource corsConfigSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));
        corsConfig.addAllowedOriginPattern("*"); // TODO: 2023-10-29 프론트 도메인이 정해지면 수정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
