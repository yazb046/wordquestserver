package com.wordquest.server.security.core.config;


import com.wordquest.server.security.core.UsernamePasswordAuthenticationProvider;
import com.wordquest.server.security.core.filter.LoginAuthenticationFilter;
import com.wordquest.server.security.core.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;


    @Bean
    protected AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   LoginAuthenticationFilter initialAuthenticationFilter,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception
    {
        http
                .csrf(csrf -> csrf.disable())
                .cors(c -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(List.of("http://localhost:5173"));
                        config.setAllowedMethods(List.of("GET", "POST"));
                        config.setAllowedHeaders(List.of("*"));
                        config.setExposedHeaders(List.of("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    };
                    c.configurationSource(source);
                })
                .addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, LoginAuthenticationFilter.class)
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/sign-up", "/login", "/api/images/**").permitAll()
                                .anyRequest().authenticated()
                );

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(usernamePasswordAuthenticationProvider);
    }

}
