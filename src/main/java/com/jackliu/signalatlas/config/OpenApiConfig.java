package com.jackliu.signalatlas.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI signalAtlasOpenAPI(){
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local development server");

        Contact contact = new Contact();
        contact.setName("Jack Liu");
        contact.setEmail("Liuziyang56@hotmail.com");

        License license = new License();
        license.setName("MIT License");

        Info info = new Info()
                .title("Signal Atlas API")
                .version("1.0.0")
                .description(
                        "A Spring Boot REST API for cataloging vintage two-way radios, " +
                        "brands, model details, technical specifications, " +
                        "search, filtering, pagination, and structured error handling."
                )
                .contact(contact)
                .license(license);
        return new OpenAPI().info(info).servers(List.of(localServer));
    }
}
