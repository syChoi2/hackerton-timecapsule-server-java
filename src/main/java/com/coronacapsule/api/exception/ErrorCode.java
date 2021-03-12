package com.coronacapsule.api.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum ErrorCode {

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "CM001", "일시적인 서버 오류"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "CM002", "권한 없음"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CM003", "요청 결과를 찾을 수 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "CM004", "잘못된 요청입니다."),
    INSUFFICIENT_VALUE(HttpStatus.BAD_REQUEST.value(), "CM005", "요청값이 부족합니다."),
    INVALID_FORMAT(HttpStatus.BAD_REQUEST.value(), "CM006", "잘못된 형식입니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST.value(), "CM007", "접근 권한이 만료되었습니다."),
    
    OPEN_NOT_ALLOWED(HttpStatus.BAD_REQUEST.value(), "CM008", "캡슐은 코로나가 끝나야 열어볼 수 있어요"),

    // User Controller
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "US001", "사용자를 찾을 수 없습니다."),
    TOKEN_ERROR(HttpStatus.BAD_REQUEST.value(), "US002", "토큰오류입니다."),
    NICKNAME_TOO_LONG(HttpStatus.BAD_REQUEST.value(), "US003", "닉네임은 10자 이내로 입력해주세요"),
    
    //Capsule Controller
    CAPSULE_NAME_TOO_LONG(HttpStatus.BAD_REQUEST.value(), "CP001", "캡슐 이름은 10자 이내로 입력해주세요"),
	
	// marble controller
	NO_EMPTY_SPACE(HttpStatus.BAD_REQUEST.value(), "MB001", "캡슐에 더 이상 공간이 없어요😢"),
	MARBLE_CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST.value(), "MB002", "소원은 21자 까지만 입력할 수 있어요");


    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}