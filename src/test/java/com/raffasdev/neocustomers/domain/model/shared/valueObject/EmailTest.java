package com.raffasdev.neocustomers.domain.model.shared.valueObject;

import com.raffasdev.neocustomers.domain.exception.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    private final String validEmail = "teste@email.com";

    @Test
    @DisplayName("of should return Email when valid email is provided")
    void of_ReturnsEmail_WhenValidEmailIsProvided() {

        Email email = assertDoesNotThrow(() -> Email.of(validEmail));

        assertNotNull(email);
        assertEquals(validEmail, email.getValue());
    }

    @Test
    @DisplayName("newEmail should return Email when valid email is provided")
    void newEmail_ReturnsValidEmail_WhenValidEmailIsProvided() {

        Email email = assertDoesNotThrow(() -> Email.newEmail(validEmail));

        assertNotNull(email);
        assertEquals(validEmail, email.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalidemail", "email@.com", "@teste.com"})
    @DisplayName("newEmail should throw InvalidEmailException when invalid values are provided")
    void newEmail_ThrowsInvalidEmailException_WhenInvalidValuesEmailAreProvided(String invalidEmail) {

        assertThrows(InvalidEmailException.class, () -> Email.newEmail(invalidEmail));
    }

    @Test
    @DisplayName("equals should return true when emails are equal")
    void equals_ReturnsTrue_WhenEmailsAreEqual() {

        Email email1 = Email.of(validEmail);

        Email email2 = Email.of(validEmail);

        assertEquals(email1, email2);
        assertEquals(email1.hashCode(), email2.hashCode());
    }

    @Test
    @DisplayName("getValue should return the email value")
    void getValue_ReturnsTheEmailValue_WhenSuccessful() {

        Email email1 = Email.of(validEmail);

        assertEquals(email1.getValue(), validEmail);
    }
}