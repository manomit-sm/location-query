package com.bsolz.locationquery.config;

import com.bsolz.locationquery.exception.InputValidationException;
import com.bsolz.locationquery.exception.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BiFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;

@Configuration
@RequiredArgsConstructor
public class LocationRouterConfig {

    private final LocationRequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> mainRouterFunction() {
        return RouterFunctions.route()
                .path("location", this::responseRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> responseRouterFunction() {
        return RouterFunctions.route()
                //.GET("", queryParam("geoType", t -> true), requestHandler::findByGeoType)
                //.GET("all", requestHandler::allLocation)
                .GET("search",
                        RequestPredicates
                                .all()
                                .and(queryParam("name", t -> true)
                                .and(queryParam("geoType", t -> true))),
                requestHandler::findByNameAndGeoType)
                .GET("search-by-name", queryParam("name", t -> true), requestHandler::findByName)
                //.filter(noQueryParamFound())
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> noQueryParamFound() {
        return (request, next) -> next.handle(request)
                .onErrorResume(InputValidationException.class, e -> ServerResponse.badRequest().build());
                //.onErrorResume(DataNotFoundException.class, e -> ServerResponse.badRequest().build());
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> {
            var ex = (InputValidationException) err;
            return ServerResponse.badRequest().bodyValue(new ApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), new SimpleDateFormat("MMM dd,yyyy HH:mm").format(new Date(System.currentTimeMillis()))));
        };
    }
}
