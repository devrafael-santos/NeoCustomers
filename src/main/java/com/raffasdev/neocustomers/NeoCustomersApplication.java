package com.raffasdev.neocustomers;

import com.raffasdev.neocustomers.config.EnvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NeoCustomersApplication {

    public static void main(String[] args) {
        var app = new SpringApplication(NeoCustomersApplication.class);
        app.addInitializers(new EnvInitializer());
        app.run(args);
    }

}
