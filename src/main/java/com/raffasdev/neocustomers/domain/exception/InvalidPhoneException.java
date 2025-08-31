package com.raffasdev.neocustomers.domain.exception;

public class InvalidPhoneException extends RuntimeException {
    public InvalidPhoneException(String message) {
        super("Invalid phone number: " + message);
    }
}