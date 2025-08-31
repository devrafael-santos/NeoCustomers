package com.raffasdev.neocustomers.application.service;

import com.raffasdev.neocustomers.application.exception.EmailAlreadyExistsException;
import com.raffasdev.neocustomers.application.exception.UserNotFoundException;
import com.raffasdev.neocustomers.application.exception.WrongCredentialsException;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository.UserRepository;
import com.raffasdev.neocustomers.infrastructure.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private TokenService tokenServiceMock;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("registerUser should register new user when valid data provided")
    void registerUser_RegistersNewUser_WhenValidDataProvided() {

        var expectedUser = User.create(
                EntityId.newId(),
                Name.newUsername("username"),
                Email.newEmail("teste@gmail.com"),
                Set.of(Role.USER_ADMIN),
                "encodedPassword123"
        );

        given(userRepositoryMock.existsByEmail(ArgumentMatchers.anyString())).willReturn(false);
        given(userRepositoryMock.save(ArgumentMatchers.any(User.class))).willReturn(expectedUser);

        given(passwordEncoderMock.encode(ArgumentMatchers.anyString())).willReturn("encodedPassword");

        User user = authService.registerUser(
                "username",
                "teste@email.com",
                Set.of(Role.USER_ADMIN),
                "password");

        assertNotNull(user);
        assertEquals(expectedUser, user);
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getEncodedPassword(), user.getEncodedPassword());
    }

    @Test
    @DisplayName("registerUser should throw EmailAlreadyExistsException when email already exists")
    void registerUser_ThrowsEmailAlreadyExistsException_WhenEmailAlreadyExists() {

        var expectedUser = User.create(
                EntityId.newId(),
                Name.newUsername("username"),
                Email.newEmail("teste@gmail.com"),
                Set.of(Role.USER_ADMIN),
                "encodedPassword123"
        );

        given(userRepositoryMock.existsByEmail(ArgumentMatchers.anyString())).willReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> authService.registerUser(
                expectedUser.getName(),
                expectedUser.getEmail(),
                Set.of(Role.USER_ADMIN),
                "password")
        );
    }

    @Test
    @DisplayName("loginUser should return a token when credentials are valid")
    void loginUser_returnsToken_whenCredentialsAreValid() {

        String email = "teste@email.com";
        String rawPassword = "password123";
        String expectedToken = "token";

        User foundUser = User.create(
                EntityId.newId(),
                Name.newUsername("username"),
                Email.newEmail("teste@gmail.com"),
                Set.of(Role.USER_ADMIN),
                "encodedPassword123"
        );

        given(userRepositoryMock.findByEmail(email)).willReturn(Optional.of(foundUser));
        given(passwordEncoderMock.matches(rawPassword, foundUser.getEncodedPassword())).willReturn(true);
        given(tokenServiceMock.generateToken(foundUser.getEmail())).willReturn(expectedToken);

        String actualToken = authService.loginUser(email, rawPassword);

        assertThat(actualToken).isEqualTo(expectedToken);

        verify(userRepositoryMock, times(1)).findByEmail(email);
        verify(passwordEncoderMock, times(1)).matches(rawPassword, foundUser.getEncodedPassword());
        verify(tokenServiceMock, times(1)).generateToken(foundUser.getEmail());
    }

    @Test
    @DisplayName("loginUser should throw UserNotFoundException when user does not exist")
    void loginUser_throwsUserNotFoundException_whenUserDoesNotExist() {

        String email = "teste@email.com";
        String rawPassword = "password123";
        given(userRepositoryMock.findByEmail(email)).willReturn(Optional.empty());

        assertThatThrownBy(() -> authService.loginUser(email, rawPassword))
                .isInstanceOf(UserNotFoundException.class);

        verify(passwordEncoderMock, never()).matches(anyString(), anyString());
        verify(tokenServiceMock, never()).generateToken(anyString());
    }

    @Test
    @DisplayName("loginUser should throw BadCredentialsException when password is incorrect")
    void loginUser_throwsBadCredentialsException_whenPasswordIsIncorrect() {

        String email = "teste@email.com";
        String rawPassword = "password123";

        User foundUser = User.create(
                EntityId.newId(),
                Name.newUsername("username"),
                Email.newEmail("teste@gmail.com"),
                Set.of(Role.USER_ADMIN),
                "encodedPassword123"
        );

        given(userRepositoryMock.findByEmail(email)).willReturn(Optional.of(foundUser));
        given(passwordEncoderMock.matches(rawPassword, foundUser.getEncodedPassword())).willReturn(false);

        assertThatThrownBy(() -> authService.loginUser(email, rawPassword))
                .isInstanceOf(WrongCredentialsException.class);

        verify(userRepositoryMock, times(1)).findByEmail(email);
        verify(passwordEncoderMock, times(1)).matches(rawPassword, foundUser.getEncodedPassword());
        verify(tokenServiceMock, never()).generateToken(anyString());
    }


}