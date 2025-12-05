package com.example.api_usuarios_spring.handler;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
