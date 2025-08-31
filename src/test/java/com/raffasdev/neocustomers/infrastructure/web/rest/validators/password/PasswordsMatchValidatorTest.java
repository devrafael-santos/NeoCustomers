package com.raffasdev.neocustomers.infrastructure.web.rest.validators.password;

import com.raffasdev.neocustomers.infrastructure.web.rest.dto.request.RegisterUserRequest;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PasswordsMatchValidatorTest {

    @Mock
    private ConstraintValidatorContext contextMock;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder violationBuilderMock;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderMock;

    @InjectMocks
    private PasswordsMatchValidator validator;

    @Test
    @DisplayName("isValid should return true when passwords match")
    void isValid_ReturnsTrue_WhenPasswordsMatch() {

        var request = new RegisterUserRequest(
                "name",
                "teste@email.com",
                Set.of("ROLE_ADMIN"),
                "123",
                "123");

        boolean result = validator.isValid(request, contextMock);

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false when passwords do not match")
    void isValid_ReturnsFalse_WhenPasswordsDoNotMatch() {

        given(contextMock.buildConstraintViolationWithTemplate(ArgumentMatchers.anyString())).willReturn(violationBuilderMock);
        given(violationBuilderMock.addPropertyNode(ArgumentMatchers.anyString())).willReturn(nodeBuilderMock);

        var request = new RegisterUserRequest(
                "name",
                "teste@email.com",
                Set.of("ROLE_ADMIN"),
                "123",
                "321");

        boolean result = validator.isValid(request, contextMock);

        assertFalse(result);

        verify(contextMock, times(1)).disableDefaultConstraintViolation();
        verify(contextMock, times(1)).buildConstraintViolationWithTemplate("Passwords do not match");
        verify(violationBuilderMock, times(1)).addPropertyNode("confirmPassword");
        verify(nodeBuilderMock, times(1)).addConstraintViolation();
    }

}