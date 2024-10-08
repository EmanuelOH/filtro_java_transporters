package com.irwi.transporters.application.exceptions;

import com.irwi.transporters.application.dtos.exceptions.ExceptionBasic;
import com.irwi.transporters.application.dtos.exceptions.ExceptionsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequest {
    //Maneja exceptiones de tipo MethodArgumentNotValidException y HttpMessageNotReadableException
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ExceptionBasic badRequest(Exception exception){
        List<String> errors = new ArrayList<>();

        // Si la excepción es de tipo MethodArgumentNotValidException,
        // se extraen los mensajes de error asociados a los argumentos no válidos.
        if(exception instanceof MethodArgumentNotValidException e){
            e.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        }else{
            errors.add(exception.getMessage());
        }

        return ExceptionsResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(errors)
                .build();
    }
}
