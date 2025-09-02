package com.raffasdev.neocustomers.domain.model.customer.valueObject;

import com.raffasdev.neocustomers.domain.exception.InvalidBirthDateException;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.ValueObject;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class BirthDate extends ValueObject {

    private final LocalDate birthDate;

    private BirthDate(LocalDate value) {

        this.birthDate = value;
        validate();
    }

    public static BirthDate of(LocalDate birthDate) {

        return new BirthDate(birthDate);
    }

    public static BirthDate newBirthDate(LocalDate birthDate) {

        return new BirthDate(birthDate);
    }

    public LocalDate getValue() {

        return birthDate;
    }

    public int getAge() {

        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    private void validate() {

        if (birthDate == null || birthDate.isAfter(LocalDate.now()) || getAge() < 18) {

            throw new InvalidBirthDateException(birthDate);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BirthDate)) return false;
        BirthDate birthDate = (BirthDate) o;
        return Objects.equals(birthDate, birthDate.birthDate);
    }

    @Override
    public int hashCode() {
        return 31 + (birthDate != null ? birthDate.hashCode() : 0);
    }
}