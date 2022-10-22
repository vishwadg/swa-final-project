package com.tutor.notificationservice.consumers;

import com.example.commonsmodule.DTOs.TutorDTO;
import com.tutor.notificationservice.entity.TutorEmail;
import com.tutor.notificationservice.service.TutorEmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TutorServiceKafkaConsumer {

    private final TutorEmailNotificationService tutorEmailNotificationService;

    @KafkaListener(topics = {"${spring.kafka.custom.tutor-topic}"}, containerFactory = "kafkaListenerJsonFactory",
    groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.custom.enable-listeners}")
    public void consumeTutorServiceDTO(TutorDTO tutorDTO){
        log.info("Received TutorDTO {}", tutorDTO);

        TutorEmail tutorEmail = new TutorEmail();
        tutorEmail.setTo(tutorDTO.getEmail());
        tutorEmail.setSubject("Welcome to Tutor Finder");
        tutorEmail.setText("Hi " + tutorDTO.getName() + "\n\n" + "You have successfully registered to the Tutor Finder");

        tutorEmailNotificationService.sendEmail(tutorEmail);
        log.info("Successfully sent email to Tutor {}", tutorEmail);
    }
}
