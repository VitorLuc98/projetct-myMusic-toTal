package com.ciandt.summit.bootcamp2022.services.exceptions;

public class MusicLimitAchievedException extends RuntimeException{
    public MusicLimitAchievedException() {
    }

    public MusicLimitAchievedException(String message) {
        super(message);
    }
}
