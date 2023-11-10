package com.lim1t.springcloudgateway.filter;

import com.lim1t.springcloudgateway.filter.EurekaClientValidationFilter.Config;
import java.util.List;
import lombok.Getter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class EurekaClientValidationFilter extends AbstractGatewayFilterFactory<Config> {

    private final DiscoveryClient discoveryClient;

    public EurekaClientValidationFilter(DiscoveryClient discoveryClient) {
        super(Config.class);
        this.discoveryClient = discoveryClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String clientName = exchange.getRequest().getHeaders()
                    .getFirst(config.CLIENT_NAME_HEADER);

            boolean isValidClient = validateClientName(clientName);

            if (!isValidClient) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }

    private Boolean validateClientName(String clientName) {
        List<String> serviceNames = discoveryClient.getServices();
        return serviceNames.contains(clientName);
    }

    @Getter
    public static class Config {

        private final String CLIENT_NAME_HEADER = "client-name";
    }
}
