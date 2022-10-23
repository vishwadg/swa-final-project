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
public class SendEmailToStudentAfterReservationKafkaConsumer {

    private final TutorEmailNotificationService tutorEmailNotificationService;

    @KafkaListener(topics = {"${spring.kafka.custom.student-detail-after-reservation-email-topic}"}, containerFactory = "kafkaListenerJsonFactory",
            groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.custom.enable-listeners}")
    public void consumeSendEmailToStudentAfterReservationDTO(UserDTO userDTO) {
        log.info("Received UserDTO {}", userDTO);

        Email tutorEmail = new Email();
        tutorEmail.setTo(userDTO.getUsername());
        tutorEmail.setSubject("Tutor Finder: Tutor Requirement Reservation");
        tutorEmail.setText("Hi " + userDTO.getUsername() + "\n\n" + "Your Tutor requirement has been reserved.");

        tutorEmailNotificationService.sendEmail(tutorEmail);
        log.info("Successfully sent email to Tutor {}", tutorEmail);
    }
}
