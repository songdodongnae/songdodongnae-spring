package com.culturefinder.songdodongnae.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(400, "파라미터 값을 확인해주세요."),
    EMPTY_FILE(400, "파일을 등록해주세요"),

    // 404 NOT_FOUND 잘못된 리소스 접근
    RESOURCE_NOT_FOUND(404, "리소스를 찾을 수 없습니다."),

    // 409 CONFLICT 서버의 상태와 충돌 (중복 등)
    SERIES_OVER_MAX(409, "최대 시리즈 개수를 초과했습니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 서버 관리자에게 연락해주세요.");

    private final int status;
    private final String message;

}
