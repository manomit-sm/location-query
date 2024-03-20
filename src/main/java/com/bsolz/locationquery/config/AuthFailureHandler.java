package com.bsolz.locationquery.config;

import com.bsolz.locationquery.exception.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@Slf4j
public class AuthFailureHandler {
    public Mono<Void> formatResponse(ServerHttpResponse response, String errorMessage) {
        response.getHeaders()
                .add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();

        ApiResponse apiResponse = new ApiResponse(Objects.requireNonNull(response.getStatusCode())
                .value(), errorMessage, new SimpleDateFormat("MMM dd,yyyy HH:mm").format(new Date(System.currentTimeMillis())));
        StringBuilder json = new StringBuilder();
        try {
            json.append(mapper.writeValueAsString(apiResponse));
        } catch (JsonProcessingException ex) {
            log.error("Exception Details {} ", ex.getMessage(), ex);
        }

        String responseBody = json.toString();
        byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory()
                .wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }
}
