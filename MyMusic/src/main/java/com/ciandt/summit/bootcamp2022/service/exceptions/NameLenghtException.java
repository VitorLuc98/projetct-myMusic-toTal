package com.ciandt.summit.bootcamp2022.service.exceptions;

public class NameLenghtException extends RuntimeException {
    public NameLenghtException() {
        super();
    }

    public NameLenghtException(String message) {
        super(message);
    }
}
