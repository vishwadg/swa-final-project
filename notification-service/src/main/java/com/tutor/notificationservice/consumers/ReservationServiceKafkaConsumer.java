package com.tutor.notificationservice.consumers;

import com.example.commonsmodule.DTOs.TutorDTO;
import com.tutor.notificationservice.entity.TutorEmail;
import com.tutor.notificationservice.service.TutorEmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceKafkaConsumer {

    private final TutorEmailNotificationService tutorEmailNotificationService;
    public void consumeReservationServiceDTO(){
        //will be updated
        //log.info("Received ReservationDTO {}", tutorDTO);

        TutorEmail tutorEmail = new TutorEmail();

        tutorEmailNotificationService.sendEmail(tutorEmail);
        log.info("Successfully sent email to Tutor {}", tutorEmail);
    }
}
