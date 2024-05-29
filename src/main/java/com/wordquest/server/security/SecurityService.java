package com.wordquest.server.security;


import com.wordquest.server.security.core.JpaUserDetailsService;
import com.wordquest.server.security.model.SecurityAuthority;
import com.wordquest.server.security.model.SecurityAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.wordquest.server.security.model.SecurityAuthorityType.USER;
import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class SecurityService {

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;
    @Autowired
    private SecurityAuthorityRepository securityAuthorityRepository;


    public void signUp(UserDto user){
        SecurityAuthority authority = securityAuthorityRepository.findByAuthority(USER.name())
                .orElse(new SecurityAuthority(USER.name()));

        jpaUserDetailsService.createUser(withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(Set.of(authority))
                .build());
    }
}