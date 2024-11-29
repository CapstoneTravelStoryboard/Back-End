package com.example.travel_sculptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableJpaAuditing
public class TravelSculptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelSculptorApplication.class, args);
    }

}
