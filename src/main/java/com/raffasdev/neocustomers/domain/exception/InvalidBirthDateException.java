package com.raffasdev.neocustomers.domain.exception;

import java.time.LocalDate;

public class InvalidBirthDateException extends RuntimeException {
    public InvalidBirthDateException(LocalDate birthDate) {
        super("Invalid birth date: " + birthDate);
    }
}
