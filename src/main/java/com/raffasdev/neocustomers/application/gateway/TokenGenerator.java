package com.raffasdev.neocustomers.application.gateway;

public interface TokenGenerator {

    String generateToken(String email);

    String validateToken(String token);
}
