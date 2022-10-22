package com.tutor.notificationservice.consumers;

import com.example.commonsmodule.DTOs.UserDTO;
import com.tutor.notificationservice.entity.Email;
import com.tutor.notificationservice.service.TutorEmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentSignupEmailSendKafkaConsumer {
    private final TutorEmailNotificationService tutorEmailNotificationService;

    @KafkaListener(topics = {"${spring.kafka.custom.student-signup-topic}"}, containerFactory = "kafkaListenerJsonFactory",
            groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.custom.enable-listeners}")
    public void consumeStudentSignUpEmailDTO(UserDTO userDTO) {
        log.info("Received UserDTO {}", userDTO);

        Email tutorEmail = new Email();
        tutorEmail.setTo(userDTO.getEmail());
        tutorEmail.setSubject("Welcome to Tutor Finder");
        tutorEmail.setText("Hi " + userDTO.getName() + "\n\n" + "You have successfully registered to the Tutor Finder");

        tutorEmailNotificationService.sendEmail(tutorEmail);
        log.info("Successfully sent email to Tutor {}", tutorEmail);
    }
}
