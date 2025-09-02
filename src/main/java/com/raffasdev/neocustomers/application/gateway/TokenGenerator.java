package com.raffasdev.neocustomers.application.gateway;

import java.util.List;

public interface TokenGenerator {

    String generateToken(String email);

    String validateToken(String token);
}
