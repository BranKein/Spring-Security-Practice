package com.yhkim.springsecuritypractice.repository.entity;

import com.yhkim.springsecuritypractice.common.UuidUtils;
import com.yhkim.springsecuritypractice.controller.dto.AccountDTO;
import com.yhkim.springsecuritypractice.mapping.AccountMapper;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @NotNull
    @Column(name = "uuid")
    private String uuid;

    @NotNull
    @Column(name = "kakao_id")
    private long kakaoId;

    @NotNull
    @Column(name = "kakao_nickname")
    private String nickname;

    @Column(name = "kakao_email")
    private String email;

    @NotNull
    @Column(name = "kakao_profile_image")
    private String profileImageUrl;

    @Transient
    public static Account toAccountEntityFromLoginObject(AccountDTO.LoginObject loginObject) {
        Account account = AccountMapper.INSTANCE.accountDtoToEntity(loginObject);
        account.setUuid(UuidUtils.uuid());

        return account;
    }
}
