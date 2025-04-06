package com.culturefinder.songdodongnae.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ CustomException.class })
    public ResponseEntity<ErrorDto> handleCustomException(CustomException exception) {
        return new ErrorDto(exception.getErrorCode()).toResponseEntity();
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<String> errorMessages = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        String errorMessage = String.join(", ", errorMessages);
        return new ErrorDto(400, errorMessage).toResponseEntity();
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorDto> handleServerException(Exception exception) {
        return new ErrorDto(ErrorCode.INTERNAL_SERVER_ERROR).toResponseEntity();
    }

}