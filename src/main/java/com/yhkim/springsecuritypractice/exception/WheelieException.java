package com.yhkim.springsecuritypractice.exception;

import org.springframework.http.HttpStatus;

public class WheelieException extends Exception{
    private final HttpStatus httpStatus;

    public WheelieException (HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
    }

    public WheelieException (HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public WheelieException (HttpStatus httpStatus, String message, Exception e) {
        super(message, e);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
