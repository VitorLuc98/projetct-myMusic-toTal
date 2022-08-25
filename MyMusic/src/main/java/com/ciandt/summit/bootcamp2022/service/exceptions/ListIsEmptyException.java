package com.ciandt.summit.bootcamp2022.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ListIsEmptyException extends RuntimeException {
    public ListIsEmptyException() {
        super();
    }

    public ListIsEmptyException(String message) {
        super(message);
    }
}
