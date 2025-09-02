package com.raffasdev.neocustomers.infrastructure.web.rest.validators.role;

import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RolesValidator implements ConstraintValidator<ValidRoles, Set<String>> {

    private Set<String> validRoles;

    @Override
    public void initialize(ValidRoles constraintAnnotation) {

        validRoles = Stream.of(Role.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Set<String> roles, ConstraintValidatorContext constraintValidatorContext) {

        if (roles == null || roles.isEmpty()) {
            return false;
        }

        return validRoles.containsAll(roles);
    }
}
