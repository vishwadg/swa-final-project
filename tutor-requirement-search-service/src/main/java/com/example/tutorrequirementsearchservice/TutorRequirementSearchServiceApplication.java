package com.example.tutorrequirementsearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class TutorRequirementSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorRequirementSearchServiceApplication.class, args);
	}

}
