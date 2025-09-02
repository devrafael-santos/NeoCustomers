package com.raffasdev.neocustomers.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TokenServiceTest {

    private TokenService tokenService;

    private final String secret = "123";

    @BeforeEach
    void setUp() {

        tokenService = new TokenService();
        long expirationMinutes = 60;

        ReflectionTestUtils.setField(tokenService, "secretKey", secret);
        ReflectionTestUtils.setField(tokenService, "expirationMinutes", expirationMinutes);
    }

    @Test
    @DisplayName("generateToken should create a valid JWT when user is valid")
    void generateToken_returnsValidJwt_whenUserIsValid() {

        String userEmail = "teste@email.com";

        String token = assertDoesNotThrow(() -> tokenService.generateToken(userEmail));

        assertThat(token).isNotNull().isNotEmpty();

        String subject = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("neoCustomers")
                .build()
                .verify(token)
                .getSubject();

        assertThat(subject).isEqualTo(userEmail);
    }

    @Test
    @DisplayName("validateToken should return subject when token is valid and not expired")
    void validateToken_returnsSubject_whenTokenIsValid() {

        String userEmail = "teste@email.com";
        String validToken = assertDoesNotThrow(() -> tokenService.generateToken(userEmail));

        String subject = assertDoesNotThrow(() -> tokenService.validateToken(validToken));

        assertThat(subject).isEqualTo(userEmail);
    }

    @Test
    @DisplayName("validateToken should return null when token signature is invalid")
    void validateToken_returnsNull_whenTokenIsInvalid() {

        String otherSecret = "321";
        String invalidToken = JWT.create()
                .withIssuer("neoCustomers")
                .withSubject("teste@exemplo.com")
                .sign(Algorithm.HMAC256(otherSecret));

        String subject = tokenService.validateToken(invalidToken);

        assertThat(subject).isNull();
    }

    @Test
    @DisplayName("validateToken should return null when token is expired")
    void validateToken_returnsNull_whenTokenIsExpired() {

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String expiredToken = JWT.create()
                .withIssuer("neoCustomers")
                .withSubject("teste@exemplo.com")
                .withExpiresAt(Instant.now().minusSeconds(1))
                .sign(algorithm);

        String subject = tokenService.validateToken(expiredToken);

        assertThat(subject).isNull();
    }

    @Test
    @DisplayName("generateToken should throw RuntimeException for JWTCreationException")
    void generateToken_throwsRuntimeException_whenCreationFails() {

        String userEmail = "teste@email.com";

        ReflectionTestUtils.setField(tokenService, "secretKey", null);

        assertThatThrownBy(() -> tokenService.generateToken(userEmail))
                .isInstanceOf(RuntimeException.class);
    }
}