package com.yhkim.springsecuritypractice.service;

import com.yhkim.springsecuritypractice.common.UuidUtils;
import com.yhkim.springsecuritypractice.common.WheelieJWT;
import com.yhkim.springsecuritypractice.controller.dto.AccountDTO;
import com.yhkim.springsecuritypractice.exception.AccountException;
import com.yhkim.springsecuritypractice.exception.WheelieException;
import com.yhkim.springsecuritypractice.repository.AccountRepository;
import com.yhkim.springsecuritypractice.repository.TokenRepository;
import com.yhkim.springsecuritypractice.repository.entity.Account;
import com.yhkim.springsecuritypractice.repository.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final WheelieJWT wheelieJWT;

    public ResponseEntity<AccountDTO.LoginResponse> loginV2(AccountDTO.LoginRequest loginRequest) throws WheelieException {
        AccountDTO.LoginObject loginObject = AccountDTO.LoginObject.builder()
                .id(loginRequest.getId())
                .nickname(loginRequest.getNickname())
                .profileImageUrl(loginRequest.getProfileImageUrl())
                .build();

        Account account = Account.toAccountEntityFromLoginObject(loginObject);

        Optional<Account> optionalAccount = accountRepository.findByKakaoIdEquals(account.getKakaoId());
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
        } else {
            accountRepository.save(account);
        }
        return ResponseEntity.ok(AccountDTO.LoginResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .token(accountV2ToJWT(account))
                .build());
    }

    public AccountDTO.MyData getMyData(Account user) {
        return AccountDTO.MyData.builder()
                .nickname(user.getNickname())
                .email(user.getEmail() == null ? "" : user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }

    private String accountV2ToJWT(Account account) {
        Token token = Token.builder()
                .uuid(UuidUtils.uuid())
                .account(account.getUuid())
                .createdAt(Long.toString(Calendar.getInstance().getTimeInMillis()))
                .build();
        String JWT = wheelieJWT.makeJwtToken(token);

        Token verifiedToken = wheelieJWT.verifyToken(JWT);
        tokenRepository.saveAndFlush(verifiedToken);
        return JWT;
    }

    public Account findAccountByUuid(String uuid) throws UsernameNotFoundException {
        return accountRepository.findById(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("no_user_found"));
    }

}
