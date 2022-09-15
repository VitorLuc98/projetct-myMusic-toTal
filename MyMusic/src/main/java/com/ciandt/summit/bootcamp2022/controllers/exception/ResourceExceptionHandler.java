package com.ciandt.summit.bootcamp2022.controllers.exception;

import com.ciandt.summit.bootcamp2022.config.interceptor.exceptions.UnauthorizedException;
import com.ciandt.summit.bootcamp2022.controllers.PlaylistController;
import com.ciandt.summit.bootcamp2022.services.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ResourceExceptionHandler {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final Logger log = LoggerFactory.getLogger(ResourceExceptionHandler.class);

    @ExceptionHandler(NameLenghtException.class)
    public ResponseEntity<StandardError> exceptionInvalidName(HttpServletRequest request) {
        log.warn("Building error object: filter less than 2 characters, {}", ResourceExceptionHandler.class);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now().format(formatter));
        standardError.setStatus(status.value());
        standardError.setError("The name should have more than 2 characters");
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        log.warn("Building error object: entity not found exception, {}", ResourceExceptionHandler.class);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now().format(formatter));
        standardError.setStatus(status.value());
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardError> unauthorizedException(HttpServletRequest request) {
        log.warn("Building error object: Unauthorized exception, {}", ResourceExceptionHandler.class);

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now().format(formatter));
        standardError.setStatus(status.value());
        standardError.setError("Unauthorized! Invalid credentials!");
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(MusicExistInPlaylistException.class)
    public ResponseEntity<StandardError> musicExistInPlaylistException(MusicExistInPlaylistException e, HttpServletRequest request) {
        log.warn("Building error object: Music already exists in the playlist, {}", ResourceExceptionHandler.class);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now().format(formatter));
        standardError.setStatus(status.value());
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(MusicNotExistInPlaylistException.class)
    public ResponseEntity<StandardError> musicNotExistInPlaylistException(MusicNotExistInPlaylistException e, HttpServletRequest request) {
        log.warn("Building error object: Music does not exist in the playlist, {}", ResourceExceptionHandler.class);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now().format(formatter));
        standardError.setStatus(status.value());
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(PlaylistIsNotTheUserException.class)
    public ResponseEntity<StandardError> playlistIsNotTheUser(PlaylistIsNotTheUserException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now().format(formatter));
        standardError.setStatus(status.value());
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }


    @ExceptionHandler(MusicLimitAchievedException.class)
    public ResponseEntity<StandardError> musicLimitAchieved(MusicLimitAchievedException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now().format(formatter));
        standardError.setStatus(status.value());
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("Building error object: validation errors, {}", ResourceExceptionHandler.class);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError err = new ValidationError();

        err.setTimestamp(LocalDateTime.now().format(formatter));
        err.setStatus(status.value());
        err.setError("Validation Error");
        err.setPath(request.getRequestURI());

        for(FieldError f : e.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);

    }
}
