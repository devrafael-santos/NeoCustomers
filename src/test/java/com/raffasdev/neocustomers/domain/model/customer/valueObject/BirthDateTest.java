package com.raffasdev.neocustomers.domain.model.customer.valueObject;

import com.raffasdev.neocustomers.domain.exception.InvalidBirthDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BirthDateTest {

    @Test
    @DisplayName("of should return BirthDate when a valid date is provided")
    void of_returnsBirthDate_whenValidDateIsProvided() {

        LocalDate validDate = LocalDate.now().minusYears(25);

        BirthDate birthDate = assertDoesNotThrow(() -> BirthDate.of(validDate));

        assertNotNull(birthDate);
        assertEquals(validDate, birthDate.getValue());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidBirthDates")
    @DisplayName("of should throw InvalidBirthDateException when an invalid date is provided")
    void of_throwsInvalidBirthDateException_whenInvalidDateIsProvided(LocalDate invalidDate) {

        assertThrows(InvalidBirthDateException.class, () -> BirthDate.of(invalidDate));
    }

    private static Stream<LocalDate> provideInvalidBirthDates() {
        return Stream.of(
                null,
                LocalDate.now().plusDays(1),
                LocalDate.now().minusYears(17)
        );
    }

    @Test
    @DisplayName("equals should return true when BirthDates are equals")
    void equals_returnsTrue_whenBirthDatesAreEqual() {

        LocalDate date = LocalDate.of(1995, 10, 23);
        BirthDate birthDate1 = BirthDate.of(date);
        BirthDate birthDate2 = BirthDate.of(date);

        assertEquals(birthDate1.getValue(), birthDate2.getValue());
        assertEquals(birthDate1.hashCode(), birthDate2.hashCode());
    }

    @Test
    @DisplayName("getBirthDate should return the date value")
    void getBirthDate_returnsTheDateValue_always() {

        LocalDate expectedDate = LocalDate.of(2000, 1, 1);
        BirthDate birthDate = BirthDate.of(expectedDate);

        LocalDate actualDate = birthDate.getValue();

        assertEquals(expectedDate, actualDate);
    }

    @Test
    @DisplayName("getAge should return the correct calculated age")
    void getAge_returnsCorrectAge_always() {

        LocalDate date30YearsAgo = LocalDate.now().minusYears(30).minusDays(1);
        BirthDate birthDate = BirthDate.of(date30YearsAgo);

        int age = birthDate.getAge();

        assertEquals(30, age);
    }
}