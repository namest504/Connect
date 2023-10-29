package xyz.connect.post.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
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

//                .headers().frameOptions().sameOrigin()
//                .and()

//                .cors().configurationSource(corsConfigSource())
//                .and()
                .cors().disable()

                .csrf().disable()

                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()

//                .exceptionHandling().authenticationEntryPoint(new InvalidJwtEntryPoint())
//                .and()
                .addFilterBefore(filterExceptionHandler, DisableEncodeUrlFilter.class)
                .addFilterAfter(aclFilter, FilterExceptionHandler.class)
                .addFilterAfter(authFilter, AclFilter.class)
                .build();
    }

//    private UrlBasedCorsConfigurationSource corsConfigSource() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowCredentials(true);
//        corsConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
//        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        corsConfig.addAllowedOriginPattern("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//        return source;
//    }
}
