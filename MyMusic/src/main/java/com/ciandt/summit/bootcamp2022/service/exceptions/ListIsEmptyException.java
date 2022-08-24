package com.ciandt.summit.bootcamp2022.service.exceptions;

public class ListIsEmptyException extends RuntimeException {
    public ListIsEmptyException() {
        super();
    }

    public ListIsEmptyException(String message) {
        super(message);
    }
}
