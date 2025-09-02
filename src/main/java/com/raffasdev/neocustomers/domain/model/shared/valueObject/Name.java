package com.raffasdev.neocustomers.domain.model.shared.valueObject;

import com.raffasdev.neocustomers.domain.exception.InvalidNameException;

import java.util.Objects;

public class Name extends ValueObject {

    private final String name;

    private Name(String name) {
        this.name = name;
        validate();
    }

    public static Name newName(String name) {
        return new Name(name);
    }

    public static Name of(String name) {
        return new Name(name);
    }

    public String getValue() {
        return this.name;
    }

    private void validate() {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException(name);
        }
        if (name.length() < 2 || name.length() > 15) {
            throw new InvalidNameException(name);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return 31 * (name != null ? name.hashCode() : 0);
    }
}
