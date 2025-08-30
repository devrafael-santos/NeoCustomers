package com.raffasdev.neocustomers.domain.model.user;

import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Username;
import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private User reconstitutedUser;
    private EntityId id;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        id = EntityId.newId();
        Username username = Username.newUsername("test");
        Email email = Email.newEmail("test@test.com");
        encodedPassword = "encodedPassword123";
        Set<Role> roles = Set.of(Role.ROLE_ADMIN);

        user = User.create(id, username, email, roles, encodedPassword);
        reconstitutedUser = User.reconstitute(id, username, email, roles, encodedPassword);
    }

    @Test
    @DisplayName("create should return a valid User when correct state is provided")
    void create_returnsValidUser_WhenCorrectStateIsProvided() {
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(encodedPassword, user.getEncodedPassword());
    }

    @Test
    @DisplayName("reconstitute should return a valid User when correct state is provided")
    void reconstitute_returnsValidUser_WhenCorrectStateIsProvided() {
        assertNotNull(reconstitutedUser);
        assertEquals(id, reconstitutedUser.getId());
        assertEquals(encodedPassword, reconstitutedUser.getEncodedPassword());
        assertEquals(user.getName(), reconstitutedUser.getName());
        assertEquals(user.getEmail(), reconstitutedUser.getEmail());
        assertEquals(user.getRoles(), reconstitutedUser.getRoles());
        assertEquals(user.getEncodedPassword(), reconstitutedUser.getEncodedPassword());
    }

    @Test
    @DisplayName("hasUsername should return true when username match")
    void hasUsername_returnsTrue_WhenUsernameMatches() {
        assertTrue(user.hasUsername(Username.newUsername("test")));
    }

    @Test
    @DisplayName("hasUsername should return false when username does not match")
    void hasUsername_returnsFalse_WhenUsernameDoesNotMatch() {
        assertFalse(user.hasUsername(Username.newUsername("anotherUsername")));
    }

    @Test
    @DisplayName("hasEmail should return true when email match")
    void hasEmail_returnsTrue_WhenEmailMatches() {
        assertTrue(user.hasEmail(Email.newEmail("test@test.com")));
    }

    @Test
    @DisplayName("hasEmail should return false when email does not match")
    void hasEmail_returnsFalse_WhenEmailDoesNotMatch() {
        assertFalse(user.hasEmail(Email.newEmail("another@email.com")));
    }

    @Test
    @DisplayName("hasEncodedPassword should return true when password match")
    void hasEncodedPassword_returnsTrue_WhenPasswordMatches() {
        assertTrue(user.hasEncodedPassword("encodedPassword123"));
    }

    @Test
    @DisplayName("hasEncodedPassword should return false when password does not match")
    void hasEncodedPassword_returnsFalse_WhenPasswordDoesNotMatch() {
        assertFalse(user.hasEncodedPassword("wrongPassword"));
    }

    @Test
    @DisplayName("getters should return the correct primitive values always")
    void getters_returnCorrectPrimitiveValues_Always() {

        assertEquals("test", user.getName());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("encodedPassword123", user.getEncodedPassword());
    }

    @Test
    @DisplayName("equals should return true when User objects are equals")
    void equals_ReturnsTrue_WhenUserObjectsAreEqual() {
        User anotherUser = User.create(
                id,
                Username.newUsername("test"),
                Email.newEmail("test@test.com"),
                Set.of(Role.ROLE_ADMIN),
                "encodedPassword123"
        );

        assertEquals(user, anotherUser);
        assertEquals(user.hashCode(), anotherUser.hashCode());
    }

    @Test
    @DisplayName("equals should return false when User objects are not equals")
    void equals_ReturnsFalse_WhenUserObjectsAreNotEqual() {
        User anotherUser = User.create(
                EntityId.newId(),
                Username.newUsername("anotherUsername"),
                Email.newEmail("another@email.com"),
                Set.of(Role.ROLE_ADMIN),
                "encodedPassword123"
        );

        assertNotEquals(user, anotherUser);
        assertNotEquals(user.hashCode(), anotherUser.hashCode());
    }

}