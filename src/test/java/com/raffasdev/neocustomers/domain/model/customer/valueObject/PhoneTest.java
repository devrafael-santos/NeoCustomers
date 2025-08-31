package com.raffasdev.neocustomers.domain.model.customer;

import com.raffasdev.neocustomers.domain.exception.InvalidPhoneException;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.Phone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    private final String validPhone = "11 98765 4321";

    @Test
    @DisplayName("of should return Phone when valid phone is provided")
    void of_ReturnsPhone_WhenValidPhoneIsProvided() {

        Phone phone = assertDoesNotThrow(() -> Phone.of(validPhone));

        assertNotNull(phone);
        assertEquals(validPhone, phone.getValue());
    }

    @Test
    @DisplayName("newPhone should return Phone when valid phone is provided")
    void newPhone_ReturnsValidPhone_WhenValidPhoneIsProvided() {

        Phone phone = assertDoesNotThrow(() -> Phone.newPhone(validPhone));

        assertNotNull(phone);
        assertEquals(validPhone, phone.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1234567", "(11) 98765-432"})
    @DisplayName("newPhone should throw InvalidPhoneException when invalid values are provided")
    void newPhone_ThrowsInvalidPhoneException_WhenInvalidValuesPhoneAreProvided(String invalidPhone) {

        assertThrows(InvalidPhoneException.class, () -> Phone.newPhone(invalidPhone));
    }

    @Test
    @DisplayName("equals should return true when phones are equal")
    void equals_ReturnsTrue_WhenPhonesAreEqual() {

        Phone phone1 = Phone.of(validPhone);

        Phone phone2 = Phone.of(validPhone);

        assertEquals(phone1, phone2);
        assertEquals(phone1.hashCode(), phone2.hashCode());
    }

    @Test
    @DisplayName("getValue should return the phone value")
    void getValue_ReturnsThePhoneValue_WhenSuccessful() {

        Phone phone = Phone.of(validPhone);

        assertEquals(phone.getValue(), validPhone);
    }

}