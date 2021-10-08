package com.yhkim.springsecuritypractice.controller;

import com.yhkim.springsecuritypractice.annotations.WheelieAccount;
import com.yhkim.springsecuritypractice.controller.dto.AccountDTO;
import com.yhkim.springsecuritypractice.exception.WheelieException;
import com.yhkim.springsecuritypractice.repository.entity.Account;
import com.yhkim.springsecuritypractice.service.AccountService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(tags = "Account API")
@RequestMapping("/account")
@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<AccountDTO.LoginResponse> loginV2(@RequestBody AccountDTO.LoginRequest loginRequest) {
        try {
            return accountService.loginV2(loginRequest);
        } catch (WheelieException e) {
            log.debug("AccountController.login: " + e);

            return ResponseEntity.
                    status(e.getHttpStatus()).
                    body(AccountDTO.LoginResponse.builder()
                            .statusCode(e.getHttpStatus().value())
                            .token("")
                            .build());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<AccountDTO.MyData> getMyData(@ApiIgnore @WheelieAccount Account account) {
        AccountDTO.MyData myData = accountService.getMyData(account);
        return ResponseEntity.ok(myData);
    }

}
