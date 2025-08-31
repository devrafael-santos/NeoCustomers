package com.raffasdev.neocustomers.application.exception;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException() {
        super("Incorrect email and/or password");
    }
}
