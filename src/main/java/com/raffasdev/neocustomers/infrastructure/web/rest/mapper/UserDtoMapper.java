package com.raffasdev.neocustomers.infrastructure.web.rest.mapper;

import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.response.RegisterUserResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class UserDtoMapper {

    public Set<Role> toEnumRoles(Set<String> roles) {
        return (roles == null)
                ? Set.of()
                : roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

    public RegisterUserResponse toRegisterUserResponse(User user) {
        return new RegisterUserResponse(
                user.getName(),
                user.getEmail()
        );
    }

}
