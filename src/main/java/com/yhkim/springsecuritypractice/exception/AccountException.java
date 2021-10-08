package com.yhkim.springsecuritypractice.exception;

public class AccountException extends Exception{

    public AccountException () {
        super();
    }

    public AccountException (String message) {
        super(message);
    }

    public AccountException (String message, Exception e) {
        super(message, e);
    }
}
