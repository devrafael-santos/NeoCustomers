package com.raffasdev.neocustomers.infrastructure.persistence.jpa.mapper;

import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Username;
import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    @DisplayName("toEntity should map all fields from User to UserEntity when successful")
    void toEntity_MapsAllFieldsFromUserToUserEntity_WhenSuccessful() {

        var id = EntityId.newId();
        User userDomain = User.create(
                id,
                Username.newUsername("username"),
                Email.newEmail("test@gmail.com"),
                Set.of(Role.ROLE_ADMIN),
                "encodedPassword123"
        );

        UserEntity resultEntity = userMapper.toEntity(userDomain);

        assertNotNull(resultEntity);
        assertEquals(id.getValue(), resultEntity.getUserId());
        assertEquals("username", resultEntity.getUsername());
        assertEquals("test@gmail.com", resultEntity.getEmail());
        assertEquals(Set.of(Role.ROLE_ADMIN), resultEntity.getRoles());
        assertEquals("encodedPassword123", resultEntity.getPassword());
    }

    @Test
    @DisplayName("toDomain should map all fields from UserEntity to User when successful")
    void toDomain_MapsAllFieldsFromEntityToDomain_WhenSuccessful() {

        var id = EntityId.newId().getValue();
        UserEntity userEntity = UserEntity.create(
                id,
                "username",
                "test@gmail.com",
                Set.of(Role.ROLE_ADMIN),
                "encodedPassword123"
        );

        User resultDomain = userMapper.toDomain(userEntity);

        assertNotNull(resultDomain);
        assertEquals(id, resultDomain.getId().getValue());
        assertEquals("username", resultDomain.getUsername());
        assertEquals("test@gmail.com", resultDomain.getEmail());
        assertEquals(Set.of(Role.ROLE_ADMIN), resultDomain.getRoles());
        assertEquals("encodedPassword123", resultDomain.getEncodedPassword());
    }

    @Test
    @DisplayName("toDomain should return null when UserEntity null is provided")
    void toDomain_ReturnsNull_WhenUserEntityNullIsProvided() {

        UserEntity userEntity = null;

        assertNull(userMapper.toDomain(userEntity));
    }

    @Test
    @DisplayName("toOptionalDomain should return Optional User when Optional UserEntity Is provided")
    void toOptionalDomain_ReturnsOptionalUser_WhenOptionalUserEntityIsProvided() {

        var optionalUserEntity = Optional.of(UserEntity.create(
                UUID.randomUUID(),
                "username",
                "test@gmail.com",
                Set.of(Role.ROLE_ADMIN),
                "encodedPassword123"
        ));

        Optional<User> optionalUser = userMapper.toOptionalDomain(optionalUserEntity);

        assertNotNull(optionalUser);
        assertEquals(optionalUserEntity.get().getUserId(), optionalUser.get().getId().getValue());
    }

    @Test
    @DisplayName("toOptionalDomain should return null when Optional UserEntity is empty")
    void toOptionalDomain_ReturnsOptionalUser_WhenOptionalUserEntityIsEmpty() {


        Optional<User> optionalUser = userMapper.toOptionalDomain( Optional.empty());

        assertEquals(Optional.empty(), optionalUser);
    }
}