package com.example.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class Gateway {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", p -> p
                        .path("/auth/**", "/user/**")
                        .uri("http://localhost:8080"))
                .route("car-service", p -> p
                        .path("/car/**")
                        .uri("http://localhost:8082"))
                .route("booking-service", p -> p
                        .path("/booking/**")
                        .uri("http://localhost:8083"))
                .route("payment-service", p -> p
                        .path("/payment/**")
                        .uri("http://localhost:8084"))
                .build();
    }
}
