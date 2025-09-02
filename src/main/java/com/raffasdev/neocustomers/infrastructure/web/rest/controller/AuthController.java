package com.raffasdev.neocustomers.infrastructure.web.rest.controller;

import com.raffasdev.neocustomers.domain.model.user.IAuthService;
import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.request.LoginUserRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.request.RegisterUserRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.response.LoginUserResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.user.response.RegisterUserResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.mapper.UserDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "1. Autenticação", description = "Endpoints para registro e autenticação de usuários do sistema")
public class AuthController {

    private final IAuthService authService;

    private final UserDtoMapper userDtoMapper;

    @Operation(
            summary = "Registra um novo usuário no sistema",
            description = "Cria uma nova identidade de usuário (operador) que poderá se autenticar para usar a API."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos (ex: email com formato incorreto, senha não bate)"),
            @ApiResponse(responseCode = "409", description = "Email ou nome de usuário já cadastrado")
    })
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

    @Operation(
            summary = "Autentica um usuário e retorna um token JWT",
            description = "Valida as credenciais de um usuário e, se bem-sucedido, retorna um token de acesso para ser usado nos endpoints protegidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida, token retornado"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas (email ou senha incorretos)")
    })
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
