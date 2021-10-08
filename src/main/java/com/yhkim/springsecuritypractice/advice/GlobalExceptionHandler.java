package com.yhkim.springsecuritypractice.advice;

import com.yhkim.springsecuritypractice.exception.AccountException;
import com.yhkim.springsecuritypractice.exception.WheelieException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WheelieException.class)
    protected ResponseEntity<?> handleWheelieException(WheelieException e) {
        log.error(e.getClass().getName() + ": " + e);
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(AccountException.class)
    protected ResponseEntity<?> handleAccountException(AccountException e) {
        log.error("There was a problem with Account");
        log.error("Error", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
