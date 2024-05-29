package com.wordquest.server.security.core.filter;

import com.wordquest.server.security.model.SecurityUserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Component
public class LoginAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager manager;
    private final SecretKey secretKey;

    private final SecurityUserRepository repository;


    public LoginAuthenticationFilter(AuthenticationManager manager, SecretKey secretKey, SecurityUserRepository repository) {
        this.manager = manager;
        this.secretKey = secretKey;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String username = request.getHeader("username");
        String password = request.getHeader("password");

        Authentication a = new UsernamePasswordAuthenticationToken(username, password);
        manager.authenticate(a);
        AtomicLong id = new AtomicLong();
        repository.findUserByUsername(username).ifPresent(securityUser -> {
            id.set(securityUser.getId());
        });
        String jwt = Jwts.builder()
                .setClaims(Map.of("username", username, "userid", id))
                .signWith(secretKey)
                .compact();

        response.setHeader("Authorization", jwt);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }

}