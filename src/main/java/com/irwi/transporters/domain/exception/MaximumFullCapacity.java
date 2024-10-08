package com.irwi.transporters.domain.exception;

public class MaximumFullCapacity extends RuntimeException{
    public MaximumFullCapacity (String message){
        super(message);
    }
}
