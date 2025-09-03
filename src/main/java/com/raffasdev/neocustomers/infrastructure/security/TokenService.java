package com.raffasdev.neocustomers.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.raffasdev.neocustomers.application.gateway.TokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService implements TokenGenerator {

    @Value("${spring.jwt.secret.key}")
    private String secretKey;

    @Value("${spring.jwt.expiration.minutes}")
    private long expirationMinutes;

    @Override
    public String generateToken(String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer("neoCustomers")
                    .withSubject(email)
                    .withExpiresAt(this.generateTokenExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer("neoCustomers")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant generateTokenExpirationDate() {

        return LocalDateTime.now().plusMinutes(this.expirationMinutes).atZone(ZoneOffset.of("-3")).toInstant();
    }
}
