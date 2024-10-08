package com.irwi.transporters.application.dtos.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ExceptionResponse extends ExceptionBasic{
    private String message;
}
