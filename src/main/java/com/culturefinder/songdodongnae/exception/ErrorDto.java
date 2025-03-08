package com.culturefinder.songdodongnae.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
public class ErrorDto {

    private final int statusCode;

    private final String message;

    public ErrorDto(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    public ResponseEntity<ErrorDto> toResponseEntity() {
        return ResponseEntity
                .status(statusCode)
                .body(this);
    }
}
