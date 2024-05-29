package com.wordquest.server.cards.repository;

import com.wordquest.server.cards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
