package com.wordquest.server.cards.model;

import com.wordquest.server.cards.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
