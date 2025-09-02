package com.raffasdev.neocustomers.infrastructure.web.rest.controller;

import com.raffasdev.neocustomers.domain.model.user.IAuthService;
import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.request.LoginUserRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.request.RegisterUserRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.response.LoginUserResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.response.RegisterUserResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    private final UserDtoMapper userDtoMapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(
            @RequestBody @Valid RegisterUserRequest registerUserRequest) {

        User userRegistered = authService.registerUser(
                registerUserRequest.getName(),
                registerUserRequest.getEmail(),
                userDtoMapper.toEnumRoles(registerUserRequest.getRoles()),
                registerUserRequest.getPassword()
        );

        return new ResponseEntity<>(
                userDtoMapper.toRegisterUserResponse(userRegistered),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(
            @RequestBody @Valid LoginUserRequest loginUserRequest) {

        String token = authService.loginUser(
                loginUserRequest.getEmail(),
                loginUserRequest.getPassword()
        );

        return new ResponseEntity<>(
                new LoginUserResponse(loginUserRequest.getEmail(), token),
                HttpStatus.OK
        );
    }

}
