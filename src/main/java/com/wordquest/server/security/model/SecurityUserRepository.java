package com.wordquest.server.security.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityUserRepository extends JpaRepository <SecurityUser, Long> {
    Optional<SecurityUser> findUserByUsername(String username);
}