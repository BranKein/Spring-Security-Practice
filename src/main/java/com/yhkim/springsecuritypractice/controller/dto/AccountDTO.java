package com.yhkim.springsecuritypractice.controller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AccountDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest extends LoginObject {
        private String digest;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginObject {
        private String nickname;
        private String id;
        private String profileImageUrl;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {
        private int statusCode;
        private String token;
    }

    @Getter
    public static class ChangeNickname {
        private String newNickname;
    }

    @Getter
    @Setter
    @Builder
    public static class MyData {
        private String nickname;
        private String email;
        private String profileImageUrl;
    }

}
