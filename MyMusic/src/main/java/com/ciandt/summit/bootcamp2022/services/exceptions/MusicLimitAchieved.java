package com.ciandt.summit.bootcamp2022.services.exceptions;

public class MusicLimitAchieved extends RuntimeException{
    public MusicLimitAchieved() {
    }

    public MusicLimitAchieved(String message) {
        super(message);
    }
}
