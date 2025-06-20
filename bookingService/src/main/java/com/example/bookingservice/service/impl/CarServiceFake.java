package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.CarDto;
import com.example.bookingservice.exception.CarNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceFake {

    private final RestTemplate restTemplate;

    private final HttpServletRequest httpServletRequest;

    public CarDto getCarById(UUID carId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            String token = httpServletRequest.getHeader("Authorization");
            if (token != null && !token.isBlank()) {
                headers.set("Authorization", token);
            }

            HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<CarDto> response = restTemplate.exchange(
                    "http://localhost:8082/car/" + carId,
                    HttpMethod.GET,
                    httpEntity,
                    CarDto.class
            );
            System.out.println(carId);
            return response.getBody();

        } catch (HttpClientErrorException.NotFound e) {
            throw new CarNotFoundException("Такой машины нет!");
        }
    }
}
