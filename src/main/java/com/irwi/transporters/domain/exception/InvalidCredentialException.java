package com.irwi.transporters.domain.exception;

public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException(String message){
        super(message);
    }
}
