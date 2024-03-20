package com.bsolz.locationquery.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.bsolz.locationquery.constant.LocationConstant.AUTHENTICATION_FAILED_MESSAGE;

@Slf4j
public class LocationAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        log.info("UNAUTHORIZED");
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return new AuthFailureHandler().formatResponse(response, AUTHENTICATION_FAILED_MESSAGE);
    }
}
