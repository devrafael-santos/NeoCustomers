package com.raffasdev.neocustomers.domain.model.shared.valueObject;

import com.raffasdev.neocustomers.domain.model.shared.excpetion.InvalidUsernameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    @DisplayName("of should return Username when valid username is provided")
    void of_ReturnsUsername_WhenValidUsernameIsProvided() {

        var validUsername = "username";

        Name name = assertDoesNotThrow(() -> Name.of(validUsername));

        assertNotNull(name);
        assertEquals(validUsername, name.getValue());
    }

    @Test
    @DisplayName("newUsername should create Username when valid value is provided")
    void newUsername_CreatesUsername_WhenValidValueIsProvided() {

        var result = assertDoesNotThrow(() -> Name.newUsername("test"));

        assertEquals("test", result.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "a", "usernameTooooLong"})
    @DisplayName("newUsername should throw InvalidUsernameException when invalid values are provided")
    void newUsername_ThrowsInvalidUsernameException_WhenInvalidValuesAreProvided(String invalidUsername) {
        assertThrows(InvalidUsernameException.class, () -> Name.newUsername(invalidUsername));
    }

    @Test
    @DisplayName("equals should return true when usernames are equals")
    void equals_returnsTrue_WhenUsernamesAreEqual() {

        Name name1 = Name.newUsername("test1");
        Name name2 = Name.newUsername("test1");

        assertEquals(name1, name2);
        assertEquals(name1.hashCode(), name2.hashCode());
    }

    @Test
    @DisplayName("equals should return false when usernames are not equals")
    void equals_returnsFalse_WhenUsernamesAreNotEqual() {

        Name name1 = Name.newUsername("test1");
        Name name2 = Name.newUsername("test2");

        assertNotEquals(name1, name2);
        assertNotEquals(name1.hashCode(), name2.hashCode());
    }

    @Test
    @DisplayName("getValue should return the username value")
    void getValue_returnsTheUsernameValue() {
        String expectedUsername = "test";
        Name name = Name.newUsername(expectedUsername);

        String actualUsername = name.getValue();

        assertEquals(expectedUsername, actualUsername);
    }

}