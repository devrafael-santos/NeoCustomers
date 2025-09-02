package com.raffasdev.neocustomers.domain.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException(String username) {
        super("Invalid username: " + username);
    }
}
