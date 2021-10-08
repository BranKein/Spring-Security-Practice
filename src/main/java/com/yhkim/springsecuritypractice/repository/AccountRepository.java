package com.yhkim.springsecuritypractice.repository;

import com.yhkim.springsecuritypractice.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByKakaoIdEquals(long kakaoId);
}
