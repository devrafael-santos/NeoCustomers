package com.raffasdev.neocustomers.domain.model.shared.valueObject;

import com.raffasdev.neocustomers.domain.exception.InvalidEmailException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email extends ValueObject {

    private final String email;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,5}$",
            Pattern.CASE_INSENSITIVE
    );

    private Email(String email) {
        this.email = email;
        validate();
    }

    public static Email of(String email) {
        return new Email(email);
    }

    public static Email newEmail(String email) {
        return new Email(email);
    }

    public String getValue() {
        return this.email;
    }

    private void validate() {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException(email);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return 31 * (email != null ? email.hashCode() : 0);
    }
}
