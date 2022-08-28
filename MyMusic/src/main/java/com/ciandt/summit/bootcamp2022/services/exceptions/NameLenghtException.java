package com.ciandt.summit.bootcamp2022.services.exceptions;

public class NameLenghtException extends RuntimeException {
    public NameLenghtException() {
        super();
    }

    public NameLenghtException(String message) {
        super(message);
    }
}
