package com.raffasdev.neocustomers.domain.model.customer.valueObject;

import com.raffasdev.neocustomers.domain.exception.InvalidCPFException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CPFTest {

    private final String validCPF = "111.111.111-11";

    @Test
    @DisplayName("of should return CPF when valid CPF is provided")
    void of_ReturnsCPF_WhenValidCPFIsProvided() {

        CPF cpf = assertDoesNotThrow(() -> CPF.of(validCPF));

        assertNotNull(cpf);
        assertEquals(validCPF, cpf.getValue());
    }

    @Test
    @DisplayName("newCPF should return CPF when valid CPF is provided")
    void newCPF_ReturnsValidCPF_WhenValidCPFIsProvided() {

        CPF cpf = assertDoesNotThrow(() -> CPF.newCPF(validCPF));

        assertNotNull(cpf);
        assertEquals(validCPF, cpf.getValue());
    }

    @Test
    @DisplayName("equals should return true when CPFs are equal")
    void equals_ReturnsTrue_WhenCPFsAreEqual() {

        CPF cpf1 = CPF.of(validCPF);

        CPF cpf2 = CPF.of(validCPF);

        assertEquals(cpf1, cpf2);
        assertEquals(cpf1.hashCode(), cpf2.hashCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1234567", "(11) 98765-432"})
    @DisplayName("newCPF should throw InvalidCPFException when invalid values are provided")
    void newCPF_ThrowsInvalidCPFException_WhenInvalidValuesCPFAreProvided(String invalidCPF) {

        assertThrows(InvalidCPFException.class, () -> CPF.newCPF(invalidCPF));
    }

    @Test
    @DisplayName("getValue should return the CPF value")
    void getValue_ReturnsTheCPFValue_WhenSuccessful() {

        CPF cpf = CPF.of(validCPF);

        assertEquals(cpf.getValue(), validCPF);
    }

}