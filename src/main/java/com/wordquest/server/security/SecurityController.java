package com.wordquest.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    public String login() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication a = securityContext.getAuthentication();
        if (a.isAuthenticated()) {
            return "Login success";
        } else {
            return "Login failed";
        }
    }

    @PostMapping(value = "/sign-up")
    public HttpStatus signUp(@RequestBody UserDto user) {
        try {
            securityService.signUp(user);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping(value = "/test")
    public List<String> test() {
        return List.of("Test1", "Test2");
    }

}