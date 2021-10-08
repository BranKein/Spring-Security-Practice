package com.yhkim.springsecuritypractice.provider;

import com.yhkim.springsecuritypractice.exception.AccountException;
import com.yhkim.springsecuritypractice.repository.entity.Account;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthenticationAccount extends AbstractAuthenticationToken {

    private Account account;
    private final String jwtTokenStr;
    private Object credentials;


    public AuthenticationAccount(String jwtTokenStr) {
        super(null);
        this.jwtTokenStr = jwtTokenStr;
        this.credentials = null;
    }

    public AuthenticationAccount(Authentication auth, Account account) {
        super(null);
        this.jwtTokenStr = (String) auth.getPrincipal();
        this.account = account;

        this.setAuthenticated(true);
    }

    // password
    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    // id
    @Override
    public Object getPrincipal() {
        return this.jwtTokenStr;
    }

    public Account getAccount() throws AccountException {
        if (this.account == null) throw new AccountException();
        return this.account;
    }
}
