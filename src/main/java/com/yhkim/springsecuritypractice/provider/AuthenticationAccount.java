package com.yhkim.springsecuritypractice.provider;

import com.yhkim.springsecuritypractice.exception.AccountException;
import com.yhkim.springsecuritypractice.repository.entity.Account;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationAccount extends AbstractAuthenticationToken {

    private final Account account;
    private Object credentials;


    public AuthenticationAccount(Account account) {
        super(null);
        this.account = account;
        this.credentials = null;
    }

    // password
    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    // id
    @Override
    public Object getPrincipal() {
        return this.account;
    }

    public Account getAccount() throws AccountException {
        if (this.account == null) throw new AccountException();
        return this.account;
    }
}
