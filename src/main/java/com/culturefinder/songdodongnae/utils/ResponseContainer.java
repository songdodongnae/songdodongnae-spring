package com.culturefinder.songdodongnae.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@ToString
@Schema(name = "ResponseContainer", description = "API 응답 컨테이너")
public class ResponseContainer<T> {

    @Schema(description = "HTTP 상태 코드")
    private int statusCode;

    @Schema(description = "메시지")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    private ResponseContainer() {}

    private ResponseContainer(HttpStatus statusCode, String message, T data) {
        this.statusCode = statusCode.value();
        this.message = message;
        this.data = data;
    }

    public static <D> ResponseEntity<ResponseContainer<D>> create(HttpStatus statusCode, String message, D data) {
        ResponseContainer<D> responseContainer = new ResponseContainer<>(statusCode, message, data);
        return ResponseEntity.status(statusCode).body(responseContainer);
    }

}
