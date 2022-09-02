package com.ciandt.summit.bootcamp2022.services.exceptions;

public class MusicExistInPlaylistException extends RuntimeException{
    public MusicExistInPlaylistException() {
        super();
    }

    public MusicExistInPlaylistException(String message) {
        super(message);
    }
}
