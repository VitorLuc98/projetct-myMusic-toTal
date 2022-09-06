package com.ciandt.summit.bootcamp2022.services.exceptions;

public class MusicNotExistInPlaylistException extends RuntimeException{
    public MusicNotExistInPlaylistException() {
        super();
    }

    public MusicNotExistInPlaylistException(String message) {
        super(message);
    }
}
