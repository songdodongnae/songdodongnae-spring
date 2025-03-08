package com.culturefinder.songdodongnae.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ CustomException.class })
    protected ResponseEntity handleCustomException(CustomException exception) {
         return new ErrorDto(exception.getErrorCode()).toResponseEntity();
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity handleServerException(Exception exception) {
        return new ErrorDto(ErrorCode.INTERNAL_SERVER_ERROR).toResponseEntity();
    }
}