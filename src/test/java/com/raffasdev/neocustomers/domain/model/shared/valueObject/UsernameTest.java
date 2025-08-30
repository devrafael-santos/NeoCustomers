package com.raffasdev.neocustomers.domain.model.shared.valueObject;

import com.raffasdev.neocustomers.domain.model.shared.excpetion.InvalidUsernameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    @Test
    @DisplayName("of should return Username when valid username is provided")
    void of_ReturnsUsername_WhenValidUsernameIsProvided() {

        var validUsername = "username";

        Username username = assertDoesNotThrow(() -> Username.of(validUsername));

        assertNotNull(username);
        assertEquals(validUsername, username.getValue());
    }

    @Test
    @DisplayName("newUsername should create Username when valid value is provided")
    void newUsername_CreatesUsername_WhenValidValueIsProvided() {

        var result = assertDoesNotThrow(() -> Username.newUsername("test"));

        assertEquals("test", result.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "a", "usernameTooooLong"})
    @DisplayName("newUsername should throw InvalidUsernameException when invalid values are provided")
    void newUsername_ThrowsInvalidUsernameException_WhenInvalidValuesAreProvided(String invalidUsername) {
        assertThrows(InvalidUsernameException.class, () -> Username.newUsername(invalidUsername));
    }

    @Test
    @DisplayName("equals should return true when usernames are equals")
    void equals_returnsTrue_WhenUsernamesAreEqual() {

        Username username1 = Username.newUsername("test1");
        Username username2 = Username.newUsername("test1");

        assertEquals(username1, username2);
        assertEquals(username1.hashCode(), username2.hashCode());
    }

    @Test
    @DisplayName("equals should return false when usernames are not equals")
    void equals_returnsFalse_WhenUsernamesAreNotEqual() {

        Username username1 = Username.newUsername("test1");
        Username username2 = Username.newUsername("test2");

        assertNotEquals(username1, username2);
        assertNotEquals(username1.hashCode(), username2.hashCode());
    }

    @Test
    @DisplayName("getValue should return the username value")
    void getValue_returnsTheUsernameValue() {
        String expectedUsername = "test";
        Username username = Username.newUsername(expectedUsername);

        String actualUsername = username.getValue();

        assertEquals(expectedUsername, actualUsername);
    }

}