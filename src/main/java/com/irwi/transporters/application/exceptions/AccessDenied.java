package com.irwi.transporters.application.exceptions;

import com.irwi.transporters.application.dtos.exceptions.ExceptionBasic;
import com.irwi.transporters.application.dtos.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccessDenied {
    @ExceptionHandler(AccessDeniedException.class)
    public ExceptionBasic handleAccessDeniedException(AccessDeniedException exception) {
        return ExceptionResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN.name())
                .message(exception.getMessage())
                .build();
    }
}
