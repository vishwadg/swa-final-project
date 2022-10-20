package com.example.tutorservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@EnableKafka
@SpringBootApplication
public class TutorServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TutorServiceApplication.class, args);
	}

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void run(String... args) throws Exception {
		kafkaTemplate.send("HELLO", "hello") ;
	}
}
