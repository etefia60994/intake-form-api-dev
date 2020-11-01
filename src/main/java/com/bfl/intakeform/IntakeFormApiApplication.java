package com.bfl.intakeform;

import com.bfl.intakeform.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class IntakeFormApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntakeFormApiApplication.class, args);
	}

}
