package com.ciandt.summit.bootcamp2022.services.exceptions;

public class PlaylistIsNotTheUserException extends RuntimeException{

    public PlaylistIsNotTheUserException() {
    }

    public PlaylistIsNotTheUserException(String message) {
        super(message);
    }
}
