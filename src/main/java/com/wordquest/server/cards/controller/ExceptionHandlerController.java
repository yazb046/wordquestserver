package com.wordquest.server.cards.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @Bean
    public ErrorAttributes errorAttributes(){
        return new DefaultErrorAttributes(){
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest req, ErrorAttributeOptions options){
                Map<String, Object> errorAttributes = super.getErrorAttributes(req, options);
                errorAttributes.remove("exception");
                return errorAttributes;
            }
        };
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException ex, HttpServletResponse resp)
            throws IOException {
        resp.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public void handleHttpServerErrorException(HttpServerErrorException ex, HttpServletResponse resp)
            throws IOException {
        resp.sendError(ex.getStatusCode().value(), ex.getMessage());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public void handleInsufficientAuthenticationException(InsufficientAuthenticationException ex, HttpServletResponse resp)
            throws IOException {
        log.error("Insufficient Authentication Exception", ex);
        resp.sendError(HttpStatus.FORBIDDEN.value(), "Insufficient Authentication");
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletResponse resp)
            throws IOException {
        log.error("Internal Error Exception", ex);
        resp.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong");
    }
}