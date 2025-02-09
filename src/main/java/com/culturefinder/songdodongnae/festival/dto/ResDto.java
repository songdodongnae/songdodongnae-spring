package com.culturefinder.songdodongnae.festival.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResDto {
    private int status;
    private String statusMessage;
    private Object result;

    public ResDto(HttpStatus status, String statusMessage, Object result) {
        this.status = status.value();
        this.statusMessage = statusMessage;
        this.result = result;
    }
}
