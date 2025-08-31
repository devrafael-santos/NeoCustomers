package com.raffasdev.neocustomers.domain.model.shared.valueObject;

import com.raffasdev.neocustomers.domain.model.shared.excpetion.InvalidUsernameException;

import java.util.Objects;

public class Name extends ValueObject {

    private final String username;

    private Name(String username) {
        this.username = username;
        validate();
    }

    public static Name newUsername(String username) {
        return new Name(username);
    }

    public static Name of(String username) {
        return new Name(username);
    }

    public String getValue() {
        return this.username;
    }

    private void validate() {
        if (username == null || username.isEmpty()) {
            throw new InvalidUsernameException(username);
        }
        if (username.length() < 2 || username.length() > 15) {
            throw new InvalidUsernameException(username);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(username, name1.username);
    }

    @Override
    public int hashCode() {
        return 31 * (username != null ? username.hashCode() : 0);
    }
}
