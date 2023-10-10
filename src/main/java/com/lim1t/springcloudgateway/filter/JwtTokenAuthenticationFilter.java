package com.lim1t.springcloudgateway.filter;

import com.lim1t.springcloudgateway.config.JwtFilterConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtTokenAuthenticationFilter extends AbstractGatewayFilterFactory<JwtFilterConfig> {

    public JwtTokenAuthenticationFilter() {
        super(JwtFilterConfig.class);
    }

    @Override
    public GatewayFilter apply(JwtFilterConfig config) {
        return (exchange, chain) -> {

            String token = exchange.getRequest()
                    .getHeaders()
                    .getFirst("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                try {
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(config.getSecretKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    exchange.getRequest().mutate()
                            .header("User-Pk", claims.getSubject())
                            .build();

                } catch (Exception e) {
                    // 토큰 파싱 실패 시 처리 로직
                    return Mono.error(new RuntimeException("Invalid Token"));
                }
            }

            return chain.filter(exchange);
        };
    }
}
