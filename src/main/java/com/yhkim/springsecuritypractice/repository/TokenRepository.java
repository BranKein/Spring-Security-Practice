package com.yhkim.springsecuritypractice.repository;

import com.yhkim.springsecuritypractice.repository.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    boolean existsByUuidEqualsAndAccountEqualsAndCreatedAtEquals(String uuid, String account, String createdAt);
}
