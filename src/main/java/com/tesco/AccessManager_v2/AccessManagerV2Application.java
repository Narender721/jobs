package com.tesco.AccessManager_v2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Reactive Endpoints",
		version = "1.0",
		description = "SpringBoot reactive endpoints using webflux"
))
public class AccessManagerV2Application {

	public static void main(String[] args) {
		SpringApplication.run(AccessManagerV2Application.class, args);
	}


}

