package com.unibuc.cinema_booking.controller.advice;

import com.unibuc.cinema_booking.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body("Invalid value : " + e.getFieldError().getRejectedValue() +
                        " for field " + e.getFieldError().getField() +
                        " with message " + e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler({EntityAlreadyExistException.class})
    public ResponseEntity<String> handle(EntityAlreadyExistException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage() + " at " + LocalDateTime.now());
    }

    @ExceptionHandler({MethodNotImplementedException.class})
    public ResponseEntity<String> handle(MethodNotImplementedException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body(e.getMessage() + " at " + LocalDateTime.now());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<String> handle(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage() + " at " + LocalDateTime.now());
    }

    @ExceptionHandler({LoginFailException.class})
    public ResponseEntity<String> handle(LoginFailException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage() + " at " + LocalDateTime.now());
    }

    @ExceptionHandler({SeatReservedException.class})
    public ResponseEntity<String> handle(SeatReservedException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage() + " at " + LocalDateTime.now());
    }
}
