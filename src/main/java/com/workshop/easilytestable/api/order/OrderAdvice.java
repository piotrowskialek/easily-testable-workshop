package com.workshop.easilytestable.api.order;

import com.workshop.easilytestable.adapters.nbp.exception.GoldExchangeRateNotFound;
import com.workshop.easilytestable.domain.order.exception.OrderNotFoundException;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class OrderAdvice {

    @ExceptionHandler(value = OrderNotFoundException.class)
    ResponseEntity<ErrorResponseDto> handleNotFound(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = GoldExchangeRateNotFound.class)
    ResponseEntity<ErrorResponseDto> handleNotFoundExchangeRates(Exception exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorResponseDto(exception.getMessage()));
    }
}

@Value
class ErrorResponseDto {
    String message;
}
