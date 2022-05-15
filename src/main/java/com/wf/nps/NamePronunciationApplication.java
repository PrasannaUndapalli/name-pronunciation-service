package com.wf.nps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Name Pronunciation API", version = "2.0", description = "Wells Fargo Employee Name Pronunciation Services"))
public class NamePronunciationApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamePronunciationApplication.class, args);
	}
}
