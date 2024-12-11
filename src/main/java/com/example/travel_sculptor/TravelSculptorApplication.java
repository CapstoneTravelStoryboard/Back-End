package com.example.travel_sculptor;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(servers = @Server(url = "https://octopus-epic-shrew.ngrok-free.app", description = "ngork Server"))
public class TravelSculptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelSculptorApplication.class, args);
    }

}
