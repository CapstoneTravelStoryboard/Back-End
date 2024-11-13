package com.example.travel_sculptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TravelSculptorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelSculptorApplication.class, args);
	}

}
