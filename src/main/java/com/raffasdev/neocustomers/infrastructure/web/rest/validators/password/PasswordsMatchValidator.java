package com.raffasdev.neocustomers.infrastructure.web.rest.validators.password;

import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.request.RegisterUserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, RegisterUserRequest> {

    @Override
    public boolean isValid(RegisterUserRequest request, ConstraintValidatorContext context) {
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPassword();

        if (Objects.equals(password, confirmPassword)) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate("Passwords do not match")
                .addPropertyNode("confirmPassword")
                .addConstraintViolation();

        return false;
    }
}
