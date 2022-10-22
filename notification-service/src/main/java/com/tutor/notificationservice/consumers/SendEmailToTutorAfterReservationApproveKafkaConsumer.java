package com.tutor.notificationservice.consumers;

import com.example.commonsmodule.DTOs.UserDTO;
import com.tutor.notificationservice.entity.Email;
import com.tutor.notificationservice.service.TutorEmailNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SendEmailToTutorAfterReservationApproveKafkaConsumer {
    private final TutorEmailNotificationService tutorEmailNotificationService;

    @KafkaListener(topics = {"${spring.kafka.custom.reservation-approved-email-topic}"}, containerFactory = "kafkaListenerJsonFactory",
            groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.custom.enable-listeners}")
    public void consumeSendEmailToTutorAfterReservationApproveDTO(UserDTO userDTO) {
        log.info("Received TutorDTO {}", userDTO);

        Email tutorEmail = new Email();
        tutorEmail.setTo(userDTO.getEmail());
        tutorEmail.setSubject("Tutor Finder: Reservation Approved");
        tutorEmail.setText("Hi " + userDTO.getName() + "\n\n" + "Your reservation has been approved.");

        tutorEmailNotificationService.sendEmail(tutorEmail);
        log.info("Successfully sent email to Tutor {}", tutorEmail);
    }
}
