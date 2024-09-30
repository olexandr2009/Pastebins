package com.example.GateWay.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    @Autowired
    public LoggingFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("{} request to {} has been received", exchange.getRequest().getMethod(), exchange.getRequest().getURI());

            log.info("Body: {}", exchange.getRequest().getBody());

            return chain.filter(exchange)
                    .contextCapture()
                    .then(Mono.fromRunnable(() -> {
                        log.info("Response with status {} has been provided: {}", exchange.getResponse().getStatusCode(), exchange.getResponse());
                    }));
        };
    }

    public static class Config {
        // Put the configuration properties
    }
}