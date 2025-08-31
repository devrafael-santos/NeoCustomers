package com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository;

import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity.UserEntity;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private IUserJpaRepository jpaRepositoryMock;

    @Mock
    private UserMapper userMapperMock;

    @InjectMocks
    private UserRepository userRepository;

    @Test
    @DisplayName("save should return a mapped domain User when save is successful")
    void save_returnsMappedUser_whenSaveIsSuccessful() {

        var id = EntityId.newId();

        User userToSave = User.create(
                id,
                Name.newUsername("username"),
                Email.newEmail("test@email.com"),
                Set.of(Role.USER_ADMIN),
                "encodedPassword"
        );

        UserEntity userEntityToSave = new UserEntity();
        UserEntity savedUserEntity = new UserEntity();

        User expectedSavedUser = User.reconstitute(
                id,
                Name.newUsername("username"),
                Email.newEmail("test@email.com"),
                Set.of(Role.USER_ADMIN),
                "encodedPassword"
        );

        given(userMapperMock.toEntity(userToSave)).willReturn(userEntityToSave);
        given(jpaRepositoryMock.save(userEntityToSave)).willReturn(savedUserEntity);
        given(userMapperMock.toDomain(savedUserEntity)).willReturn(expectedSavedUser);

        User actualSavedUser = userRepository.save(userToSave);

        assertNotNull(actualSavedUser);
        assertEquals(expectedSavedUser, actualSavedUser);

        verify(userMapperMock, times(1)).toEntity(userToSave);
        verify(userMapperMock, times(1)).toDomain(savedUserEntity);
        verify(jpaRepositoryMock, times(1)).save(userEntityToSave);
    }

    @Test
    @DisplayName("existsByEmail should return true when email exists")
    void existsByEmail_returnsTrue_whenEmailExists() {

        String email = "test@email.com";
        given(jpaRepositoryMock.existsByEmail(email)).willReturn(true);

        boolean result = userRepository.existsByEmail(email);

        assertTrue(result);
        verify(jpaRepositoryMock, times(1)).existsByEmail(email);
    }

    @Test
    @DisplayName("existsByEmail should return false when email does not exists")
    void existsByEmail_returnsFalse_whenEmailDoesNotExist() {

        String email = "test@email.com";
        given(jpaRepositoryMock.existsByEmail(email)).willReturn(false);

        boolean result = userRepository.existsByEmail(email);

        assertFalse(result);
        verify(jpaRepositoryMock, times(1)).existsByEmail(email);
    }

    @Test
    @DisplayName("findByEmail should return Email when email exists")
    void findByEmail_returnsEmail_whenEmailExists() {

        var email = "test@email.com";

        var expectedUserOptional = Optional.of(User.create(
                EntityId.newId(),
                Name.newUsername("username"),
                Email.newEmail(email),
                Set.of(Role.USER_ADMIN),
                "encodedPassword"
        ));

        var entityOptional = Optional.of(new UserEntity());

        given(jpaRepositoryMock.findByEmail(email)).willReturn(entityOptional);
        given(userMapperMock.toOptionalDomain(entityOptional)).willReturn(expectedUserOptional);

        var userOptional = userRepository.findByEmail(email);

        assertTrue(userOptional.isPresent());
        assertEquals(expectedUserOptional.get(), userOptional.get());

        verify(jpaRepositoryMock, times(1)).findByEmail(email);
        verify(userMapperMock, times(1)).toOptionalDomain(entityOptional);
    }

}