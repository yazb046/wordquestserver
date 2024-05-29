package com.wordquest.server.security.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityAuthorityRepository extends JpaRepository<SecurityAuthority, Long> {

    Optional<SecurityAuthority> findByAuthority(String authority);
}
