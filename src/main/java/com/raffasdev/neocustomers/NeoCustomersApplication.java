package com.raffasdev.neocustomers;

import com.raffasdev.neocustomers.config.EnvInitializer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "NeoCustomers - Cadastro de Clientes API",
                version = "1.0",
                description = "API REST para o desafio técnico da NeoApp, focada no gerenciamento com autenticação JWT."
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@SpringBootApplication
public class NeoCustomersApplication {

    public static void main(String[] args) {
        var app = new SpringApplication(NeoCustomersApplication.class);
        app.addInitializers(new EnvInitializer());
        app.run(args);
    }

}
