package com.raffasdev.neocustomers.infrastructure.web.rest.validators.role;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RolesValidatorTest {

    @Mock
    private ConstraintValidatorContext contextMock;

    @InjectMocks
    private RolesValidator validator;

    @BeforeEach
    void setUp() {
        validator.initialize(mock(ValidRoles.class));
    }

    @Test
    @DisplayName("isValid should return true when roles are valid")
    void isValid_returnsTrue_whenRolesAreValid() {

        Set<String> validRoles = Set.of("ROLE_ADMIN");

        boolean result = validator.isValid(validRoles, contextMock);

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false when one or more roles is invalid")
    void isValid_returnsTrue_whenOneOrMoreRolesIsValid() {

        Set<String> validRole = Set.of("ROLE_USER");

        boolean result = validator.isValid(validRole, contextMock);

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false when roles set is null or empty")
    void isValid_returnsTrue_whenRolesSetIsNullOrEmpty() {

        assertFalse(validator.isValid(null, contextMock));
        assertFalse(validator.isValid(Collections.emptySet(), contextMock));
    }

}