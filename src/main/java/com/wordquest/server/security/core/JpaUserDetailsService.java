package com.wordquest.server.security.core;


import com.wordquest.server.security.model.SecurityAuthority;
import com.wordquest.server.security.model.SecurityUser;
import com.wordquest.server.security.model.SecurityUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class JpaUserDetailsService implements UserDetailsManager {


    private final SecurityUserRepository securityUserRepository;
    private final PasswordEncoder passwordEncoder;

    public JpaUserDetailsService(SecurityUserRepository securityUserRepository, PasswordEncoder passwordEncoder) {
        this.securityUserRepository = securityUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDetails user) {
        Optional<SecurityUser> doUserExist = securityUserRepository.findUserByUsername(user.getUsername());
        if (doUserExist.isPresent()) {
            throw new BadCredentialsException("Bad credentials");
        }
        SecurityUser securityUser =
                new SecurityUser(
                        user.getUsername(),
                        passwordEncoder.encode(user.getPassword()),
                        (Set<SecurityAuthority>) user.getAuthorities());

        securityUserRepository.save(securityUser);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return securityUserRepository.findUserByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return securityUserRepository.findUserByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("No such user"));
    }

}