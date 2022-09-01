package com.ciandt.summit.bootcamp2022.services.exceptions;

public class EntityNotFoundException extends RuntimeException{
    
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
