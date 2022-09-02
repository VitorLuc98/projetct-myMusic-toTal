package com.ciandt.summit.bootcamp2022.config.interceptor.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
