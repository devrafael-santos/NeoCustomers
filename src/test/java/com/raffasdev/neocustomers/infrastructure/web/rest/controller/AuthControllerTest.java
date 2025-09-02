package com.raffasdev.neocustomers.infrastructure.web.rest.controller;

import com.raffasdev.neocustomers.domain.model.user.IAuthService;
import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.request.LoginUserRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.request.RegisterUserRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.response.LoginUserResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.response.RegisterUserResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.mapper.UserDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private IAuthService authServiceMock;

    @Mock
    private UserDtoMapper userDtoMapperMock;

    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("registerUser should return status 201 Created when data is valid")
    void registerUser_shouldReturnCreated_whenDataIsValid() {

        var requestDto = new RegisterUserRequest(
                "username",
                "teste@email.com",
                Set.of("ROLE_ADMIN"),
                "password123",
                "password123"
        );

        User userDomain = mock(User.class);
        var responseDto = new RegisterUserResponse("username", "teste@email.com");

        given(authServiceMock.registerUser(any(), any(), any(), any())).willReturn(userDomain);
        given(userDtoMapperMock.toRegisterUserResponse(userDomain)).willReturn(responseDto);

        ResponseEntity<RegisterUserResponse> responseEntity = authController.registerUser(requestDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().name()).isEqualTo("username");
        assertThat(responseEntity.getBody().email()).isEqualTo("teste@email.com");
    }

    @Test
    @DisplayName("loginUser should return status 200 OK and a token when credentials are valid")
    void loginUser_shouldReturnOkAndToken_whenCredentialsAreValid() {

        var loginRequest = new LoginUserRequest("teste@email.com", "password123");
        String expectedToken = "a.valid.jwt";

        given(authServiceMock.loginUser(loginRequest.getEmail(), loginRequest.getPassword())).willReturn(expectedToken);

        ResponseEntity<LoginUserResponse> responseEntity = authController.loginUser(loginRequest);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().token()).isEqualTo(expectedToken);
        assertThat(responseEntity.getBody().email()).isEqualTo("teste@email.com");
    }

    @Test
    @DisplayName("loginUser should throw BadCredentialsException when credentials are invalid")
    void loginUser_shouldThrowException_whenCredentialsAreInvalid() {

        var loginRequest = new LoginUserRequest("teste@email.com", "wrongpassword");

        given(authServiceMock.loginUser(any(), any())).willThrow(new BadCredentialsException("Invalid credentials"));

        assertThatThrownBy(() -> authController.loginUser(loginRequest))
                .isInstanceOf(BadCredentialsException.class);
    }
}