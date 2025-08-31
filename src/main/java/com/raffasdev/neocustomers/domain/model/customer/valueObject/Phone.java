package com.raffasdev.neocustomers.domain.model.customer.valueObject;

import com.raffasdev.neocustomers.domain.exception.InvalidPhoneException;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class Phone extends ValueObject {

    private final String phone;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\(?\\d{2}\\)?\\s?)?(9?\\d{4}[-.\\s]?\\d{4})$");

    public Phone(String phone) {
        this.phone = phone;
        validate();
    }

    public static Phone of(String phone) {
        return new Phone(phone);
    }

    public static Phone newPhone(String phone) {
        return new Phone(phone);
    }

    public String getValue() {
        return phone;
    }

    private void validate() {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new InvalidPhoneException(phone);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;
        Phone phone1 = (Phone) o;
        return Objects.equals(phone, phone1.phone);
    }

    @Override
    public int hashCode() {
        return 31 + (phone != null ? phone.hashCode() : 0);
    }
}
