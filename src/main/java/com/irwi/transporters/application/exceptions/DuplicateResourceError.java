package com.irwi.transporters.application.exceptions;


import com.irwi.transporters.application.dtos.exceptions.ExceptionBasic;
import com.irwi.transporters.application.dtos.exceptions.ExceptionResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateResourceError {
    @ExceptionHandler(DuplicateKeyException.class)
    public ExceptionBasic handleDuplicateKeyException(DuplicateKeyException exception) {
        return ExceptionResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT.name())
                .message("Duplicate resource: " + exception.getMessage())
                .build();
    }
}
