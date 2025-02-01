package com.culturefinder.songdodongnae.delicious_spot.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

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

}
