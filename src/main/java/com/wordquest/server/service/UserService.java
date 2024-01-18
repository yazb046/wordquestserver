package com.wordquest.server.service;

import com.wordquest.server.entity.UserEntity;
import com.wordquest.server.repository.UserRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> get(Long userId) {
        return userRepository.findById(userId);
    }
}
