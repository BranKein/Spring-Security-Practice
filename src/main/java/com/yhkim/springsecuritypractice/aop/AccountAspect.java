package com.yhkim.springsecuritypractice.aop;

import com.yhkim.springsecuritypractice.exception.AccountException;
import com.yhkim.springsecuritypractice.exception.WheelieException;
import com.yhkim.springsecuritypractice.filter.JwtAuthenticationFilter;
import com.yhkim.springsecuritypractice.provider.AuthenticationAccount;
import com.yhkim.springsecuritypractice.repository.entity.Account;
import com.yhkim.springsecuritypractice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class AccountAspect {

    @Around("execution(* com.yhkim.springsecuritypractice.controller.*.*(.., @com.yhkim.springsecuritypractice.annotations.WheelieAccount (*), ..)) && !@annotation(com.yhkim.springsecuritypractice.annotations.EscapeAccountAspect)")
    public Object checkToken(ProceedingJoinPoint point) throws Throwable {
        String controller = (String) point.getTarget().getClass().getSimpleName();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Object inputParam = null;
        for (Object obj : point.getArgs()) {
            if (obj instanceof Map) {
                inputParam = obj;
            }
        }
        String path = request.getRequestURI();
        int port = request.getRemotePort();
        // TODO: X-Real-IP 로 nginx가 real addr 넘겨줌
        String ipAddr = request.getRemoteAddr();
        String method = request.getMethod();
        String success = "Success";

        // TODO: Request Method도 log!!
        log.info("#####################################################################################################");
        log.info("# REQUEST | CONTROLLER = {} | METHOD = {} | PATH = {} | REMOTEADDR = {} | PORT = {} | IN_PARAMS = {}",
                controller, method, path, ipAddr, port, inputParam == null ? "": inputParam);
        log.info("#####################################################################################################");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = ((AuthenticationAccount) authentication).getAccount();
        Object[] args = Arrays.stream(point.getArgs())
                .map(data -> {
                    if (data instanceof Account) {
                        data = account;
                    }
                    return data;
                }).toArray();

        Object returnValue = point.proceed(args);

        log.info("#####################################################################################################");
        log.info("# RESPONSE | CONTROLLER = {} | METHOD = {} | PATH = {} | RESULT = {} | REMOTEADDR = {} | PORT = {} | IN_PARAMS = {}",
                controller, method, path, returnValue, ipAddr, port, inputParam == null ? "" : inputParam);
        log.info("#####################################################################################################");

        return returnValue;
    }
}
