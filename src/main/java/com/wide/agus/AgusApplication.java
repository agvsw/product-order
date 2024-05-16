package com.wide.agus;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties
@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8000", description = "Default Server URL")})
public class AgusApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgusApplication.class, args);
    }

}
