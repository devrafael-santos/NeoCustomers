package com.raffasdev.neocustomers.infrastructure.web.rest.validators.role;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RolesValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRoles {

    String message() default "One or more roles provided are invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
