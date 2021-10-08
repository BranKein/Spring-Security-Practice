package com.yhkim.springsecuritypractice.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhkim.springsecuritypractice.exception.AccountException;
import com.yhkim.springsecuritypractice.repository.AccountRepository;
import com.yhkim.springsecuritypractice.repository.TokenRepository;
import com.yhkim.springsecuritypractice.repository.entity.Account;
import com.yhkim.springsecuritypractice.repository.entity.Token;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String SECRET_KEY = "dsfjlsd346u356flsjd6nufo45367345n3y6456535ewh345ny64uswde09fu232340f2341w45by45y63nenfp08324un5g3";

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;

    public String makeJwtToken(Token token) {
        Date now = new Date();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> tokenMap = objectMapper.convertValue(token, Map.class);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(tokenMap)
                .setIssuer("Wheelie")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofDays(365 * 10).toMillis()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Authentication getAuthentication(String tokenString) {
        Token token = verifyToken(tokenString);
        if(!tokenRepository.existsByUuidEqualsAndAccountEqualsAndCreatedAtEquals(
                token.getUuid(), token.getAccount(), token.getCreatedAt())) {
            throw new AuthenticationException("no_such_token") {};
        }
        Account account = accountRepository.findById(token.getAccount())
                .orElseThrow(() -> new AuthenticationException("no_such_user"){});
        Authentication authentication =  new AuthenticationAccount(account);
        authentication.setAuthenticated(true);
        return authentication;
    }

    public String resolveToken(HttpServletRequest request) {
        String tokenStr = request.getHeader("Authorization");

        if (tokenStr == null || tokenStr.isEmpty()) {
            return null;
        }

        String[] splitToken = tokenStr.split(" ");
        if (!splitToken[0].equals("WheelieToken")) {
            throw new AuthenticationException("not_valid_token"){};
        }
        return splitToken[1];
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jwtToken);

            Date exp = claims.getBody().getExpiration();
            return !exp.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Token verifyToken(String jwtToken) {
        Claims jwtClaims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwtToken)
                .getBody();
        return Token.builder()
                .uuid(jwtClaims.get("uuid", String.class))
                .account(jwtClaims.get("account", String.class))
                .createdAt(jwtClaims.get("createdAt", String.class))
                .build();
    }
}
