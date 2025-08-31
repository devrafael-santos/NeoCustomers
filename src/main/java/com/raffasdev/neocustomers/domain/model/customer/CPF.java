package com.raffasdev.neocustomers.domain.model.customer;

import com.raffasdev.neocustomers.domain.exception.InvalidCPFException;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class CPF extends ValueObject {

    private final String cpf;

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");

    public CPF(String cpf) {
        this.cpf = cpf;
        validate();
    }

    public static CPF of(String cpf) {
        return new CPF(cpf);
    }

    public static CPF newCPF(String cpf) {
        return new CPF(cpf);
    }

    public String getValue() {
        return cpf;
    }

    private void validate() {
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            throw new InvalidCPFException(cpf);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CPF)) return false;
        CPF cpf1 = (CPF) o;
        return Objects.equals(cpf, cpf1.cpf);
    }

    @Override
    public int hashCode() {
        return 31 + (cpf != null ? cpf.hashCode() : 0);
    }
}
