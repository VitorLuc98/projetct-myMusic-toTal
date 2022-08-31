package com.ciandt.summit.bootcamp2022.controllers.exception;

import com.ciandt.summit.bootcamp2022.config.interceptor.exceptions.UnauthorizedException;
import com.ciandt.summit.bootcamp2022.services.exceptions.NameLenghtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(NameLenghtException.class)
    public ResponseEntity<StandardError> exceptionInvalidName(HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setStatus(status.value());
        standardError.setError("The name should have more than 2 characters");
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardError> unauthorizedException(HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        StandardError standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setStatus(status.value());
        standardError.setError("Unauthorized! Invalid credentials!");
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }
}
