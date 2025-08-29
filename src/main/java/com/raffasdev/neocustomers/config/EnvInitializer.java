package com.raffasdev.neocustomers.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class EnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        Dotenv baseEnv = Dotenv.configure()
                .filename(".env")
                .ignoreIfMissing()
                .load();

        Dotenv env = Dotenv.configure()
                .filename(".env." + baseEnv.get("ENVIRONMENT", "dev"))
                .ignoreIfMissing()
                .load();

        env.entries().forEach(entry -> {
            if (System.getProperty(entry.getKey()) == null) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });

        baseEnv.entries().forEach(entry -> {
            if (System.getProperty(entry.getKey()) == null) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });
    }

}
