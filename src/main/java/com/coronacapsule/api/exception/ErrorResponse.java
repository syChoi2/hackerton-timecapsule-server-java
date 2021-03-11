package com.coronacapsule.api.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;

    public ErrorResponse(ErrorCode code, String message) {
//    this.message = code.getMessage();
        this.code = code.getCode();
        this.message = message;
    }

    public static ErrorResponse of(ErrorCode code, String message) {
        return new ErrorResponse(code, message);
    }


}