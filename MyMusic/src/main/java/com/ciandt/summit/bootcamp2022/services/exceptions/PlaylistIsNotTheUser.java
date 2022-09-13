package com.ciandt.summit.bootcamp2022.services.exceptions;

public class PlaylistIsNotTheUser extends RuntimeException{

    public PlaylistIsNotTheUser() {
    }

    public PlaylistIsNotTheUser(String message) {
        super(message);
    }
}
