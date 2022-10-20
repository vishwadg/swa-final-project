package com.example.tutorservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@EnableKafka
@SpringBootApplication
public class TutorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorServiceApplication.class, args);
	}

}
