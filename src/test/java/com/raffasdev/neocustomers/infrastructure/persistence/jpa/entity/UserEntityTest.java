package com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity;

import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    @DisplayName("create should return a UserEntity with the provided parameters")
    void create_ReturnsUserEntityWithProvidedParameters() {
        var userEntity = UserEntity.create(
                UUID.randomUUID(),
                "username",
                "test@gmail.com",
                Set.of(Role.USER_ADMIN),
                "password123"
        );

        assertNotNull(userEntity);
        assertNotNull(userEntity.getUserId());
        assertEquals("username", userEntity.getName());
        assertEquals("test@gmail.com", userEntity.getEmail());
        assertEquals(Set.of(Role.USER_ADMIN), userEntity.getRoles());
        assertEquals("password123", userEntity.getPassword());
    }

    @Test
    @DisplayName("default constructor should create an empty UserEntity")
    void defaultConstructor_CreatesEmptyUserEntity() {
        var userEntity = new UserEntity();

        assertNotNull(userEntity);
        assertNull(userEntity.getUserId());
        assertNull(userEntity.getName());
        assertNull(userEntity.getEmail());
        assertEquals(0, userEntity.getRoles().size());
        assertNull(userEntity.getPassword());
    }

    @Test
    @DisplayName("getAuthorities should return an Authority Collection")
    void getAuthorities_returnsAuthorityCollection() {

        var expectedAuthorities = Set.of(Role.USER_ADMIN);

        var userEntity = UserEntity.create(
                UUID.randomUUID(),
                "username",
                "test@gmail.com",
                expectedAuthorities,
                "password123"
        );

        assertNotNull(userEntity.getAuthorities());
        assertEquals(1, userEntity.getAuthorities().size());
    }

}