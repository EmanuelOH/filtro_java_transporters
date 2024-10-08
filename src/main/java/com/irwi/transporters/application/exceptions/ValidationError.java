package com.irwi.transporters.application.exceptions;

import com.irwi.transporters.application.dtos.exceptions.ExceptionBasic;
import com.irwi.transporters.application.dtos.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationError {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionBasic handleValidationException(MethodArgumentNotValidException exception) {
        String errors = exception.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ExceptionResponse.builder()
                .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.name())
                .message(errors)
                .build();
    }
}
