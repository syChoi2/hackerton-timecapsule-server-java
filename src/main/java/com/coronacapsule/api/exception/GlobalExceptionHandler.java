package com.coronacapsule.api.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GlobalExceptionHandler {

    
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, Exception e) {
        log.error("handleException", e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.SERVER_ERROR, e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Custom Error Handler */
    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(HttpServletRequest request, BusinessException e) {
        log.error("handleBusinessException", e);
        ErrorResponse response = ErrorResponse.of(e.getErrorCode(), e.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    

}