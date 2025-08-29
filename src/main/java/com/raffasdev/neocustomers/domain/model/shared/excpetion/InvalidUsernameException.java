package com.raffasdev.neocustomers.domain.model.shared.excpetion;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException(String username) {
        super("Invalid username: " + username);
    }
}
