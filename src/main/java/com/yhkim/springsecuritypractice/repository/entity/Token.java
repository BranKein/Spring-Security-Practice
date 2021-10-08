package com.yhkim.springsecuritypractice.repository.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @NotNull
    @Column(name = "uuid")
    private String uuid;

    @NotNull
    @Column(name = "account")
    private String account;

    @NotNull
    @Column(name = "created_at")
    private String createdAt;

}
