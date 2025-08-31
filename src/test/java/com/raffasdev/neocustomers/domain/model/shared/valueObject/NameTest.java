package com.raffasdev.neocustomers.domain.model.shared.valueObject;

import com.raffasdev.neocustomers.domain.exception.InvalidUsernameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    @DisplayName("of should return Name when valid username is provided")
    void of_ReturnsName_WhenValidUsernameIsProvided() {

        var validName = "username";

        Name name = assertDoesNotThrow(() -> Name.of(validName));

        assertNotNull(name);
        assertEquals(validName, name.getValue());
    }

    @Test
    @DisplayName("newName should create Name when valid value is provided")
    void newName_CreatesName_WhenValidValueIsProvided() {

        var result = assertDoesNotThrow(() -> Name.newName("test"));

        assertEquals("test", result.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "a", "usernameTooooLong"})
    @DisplayName("newUsername should throw InvalidUsernameException when invalid values are provided")
    void newUsername_ThrowsInvalidUsernameException_WhenInvalidValuesAreProvided(String invalidUsername) {
        assertThrows(InvalidUsernameException.class, () -> Name.newName(invalidUsername));
    }

    @Test
    @DisplayName("equals should return true when usernames are equals")
    void equals_returnsTrue_WhenUsernamesAreEqual() {

        Name name1 = Name.newName("test1");
        Name name2 = Name.newName("test1");

        assertEquals(name1, name2);
        assertEquals(name1.hashCode(), name2.hashCode());
    }

    @Test
    @DisplayName("equals should return false when usernames are not equals")
    void equals_returnsFalse_WhenUsernamesAreNotEqual() {

        Name name1 = Name.newName("test1");
        Name name2 = Name.newName("test2");

        assertNotEquals(name1, name2);
        assertNotEquals(name1.hashCode(), name2.hashCode());
    }

    @Test
    @DisplayName("getValue should return the username value")
    void getValue_returnsTheUsernameValue() {
        String expectedUsername = "test";
        Name name = Name.newName(expectedUsername);

        String actualUsername = name.getValue();

        assertEquals(expectedUsername, actualUsername);
    }

}