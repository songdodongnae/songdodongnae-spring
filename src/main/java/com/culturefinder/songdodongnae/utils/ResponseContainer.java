package com.culturefinder.songdodongnae.utils;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@ToString
public class ResponseContainer<T> {

    private int statusCode;

    private String message;

    private T data;

    public ResponseContainer(HttpStatus statusCode, String message, T data) {
        this.statusCode = statusCode.value();
        this.message = message;
        this.data = data;
    }

    public ResponseEntity<ResponseContainer<T>> toResponseEntity() {
        return ResponseEntity
                .status(statusCode)
                .body(this);
    }

}
