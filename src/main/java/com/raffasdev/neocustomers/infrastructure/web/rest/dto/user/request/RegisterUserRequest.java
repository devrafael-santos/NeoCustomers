package com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.request;

import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import com.raffasdev.neocustomers.infrastructure.web.rest.validators.password.PasswordsMatch;
import com.raffasdev.neocustomers.infrastructure.web.rest.validators.role.ValidRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
@PasswordsMatch
public class RegisterUserRequest {

    @NotBlank(message = "Name of User cannot be blank")
    @Size(min = 2, max = 15, message = "Name of User must be between 2 and 15 characters")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "User must have at leas one role")
    @ValidRoles
    private Set<String> roles;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Confirm Password cannot be blank")
    private String confirmPassword;
}
