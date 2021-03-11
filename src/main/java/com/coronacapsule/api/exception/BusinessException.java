package com.coronacapsule.api.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = -3020701491173535007L;

    private ErrorCode errorCode;
        
    public BusinessException(String detailMessage, ErrorCode errorCode) {
        super(errorCode.getMessage()+" ::: "+detailMessage);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
}