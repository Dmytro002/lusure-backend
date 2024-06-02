package com.example.lesure.security.auth.service;

import org.springframework.http.HttpStatus;

public class GlobalException extends Throwable {
    public GlobalException(HttpStatus httpStatus, String userAlreadyExists) {
    }
}
