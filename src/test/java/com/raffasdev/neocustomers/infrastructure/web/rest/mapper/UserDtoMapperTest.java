package com.raffasdev.neocustomers.infrastructure.web.rest.mapper;

import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.response.RegisterUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoMapperTest {

    private UserDtoMapper userDtoMapper;

    @BeforeEach
    void setUp() {
        userDtoMapper = new UserDtoMapper();
    }

    @Test
    @DisplayName("toRegisterUserResponse maps User to RegisterUserResponse when successful")
    void toRegisterUserResponse_MapsUserToRegisterUserResponse_WhenSuccessful() {
        User user = User.create(
                EntityId.newId(),
                Name.newName("name"),
                Email.newEmail("teste@email.com"),
                Set.of(Role.USER_ADMIN),
                "encodedPassword123"
        );

        var expectedResponse = new RegisterUserResponse(
                "name",
                "teste@email.com"
        );

        var response = userDtoMapper.toRegisterUserResponse(user);

        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("toEnumRoles maps a Set of String to a Set Role when successful")
    void toEnumRoles_MapsASetOfStringToASetRole_WhenSuccessful() {

        var stringRoles = Set.of("USER_ADMIN");
        var expectedRoles = Set.of(Role.USER_ADMIN);

        var response = userDtoMapper.toEnumRoles(stringRoles);

        assertEquals(expectedRoles, response);
    }

}