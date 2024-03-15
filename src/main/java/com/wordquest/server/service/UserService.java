package com.wordquest.server.service;

import com.wordquest.server.entity.User;
import com.wordquest.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> get(Long userId) {
        return userRepository.findById(userId);
    }
}
