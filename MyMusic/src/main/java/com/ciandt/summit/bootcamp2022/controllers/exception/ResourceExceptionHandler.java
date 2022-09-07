package com.ciandt.summit.bootcamp2022.controllers.exception;

import com.ciandt.summit.bootcamp2022.config.interceptor.exceptions.UnauthorizedException;
import com.ciandt.summit.bootcamp2022.services.exceptions.MusicExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.services.exceptions.MusicNotExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.services.exceptions.NameLenghtException;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
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

    @ExceptionHandler(NameLenghtException.class)
    public ResponseEntity<StandardError> exceptionInvalidName(HttpServletRequest request) {
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
