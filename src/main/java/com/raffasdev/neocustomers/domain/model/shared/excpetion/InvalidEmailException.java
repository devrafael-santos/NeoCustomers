package com.raffasdev.neocustomers.domain.model.shared.excpetion;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String email) {
        super("Invalid email address: " + email);
    }
}
