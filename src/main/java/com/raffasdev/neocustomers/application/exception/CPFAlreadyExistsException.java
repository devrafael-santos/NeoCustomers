package com.raffasdev.neocustomers.application.exception;

public class CPFAlreadyExistsException extends RuntimeException {
    public CPFAlreadyExistsException(String cpf) {
        super("CPF already exists: " + cpf);
    }
}
