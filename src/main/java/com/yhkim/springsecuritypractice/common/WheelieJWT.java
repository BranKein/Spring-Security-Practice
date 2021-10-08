package com.yhkim.springsecuritypractice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhkim.springsecuritypractice.repository.entity.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
public class WheelieJWT {

    // TODO: custom config 파일
    private static final String SECRET_KEY = "dsfjlsd346u356flsjd6nufo45367345n3y6456535ewh345ny64uswde09fu232340f2341w45by45y63nenfp08324un5g3";

    public String makeJwtToken(Token token) {
        Date now = new Date();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> tokenMap = objectMapper.convertValue(token, Map.class);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("Wheelie")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofDays(999999999).toMillis()))
                .setClaims(tokenMap)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
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
