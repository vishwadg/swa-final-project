package com.example.tutorservice.consumers;

import com.example.commonsmodule.DTOs.TutorDTO;
import com.example.commonsmodule.DTOs.UserDTO;
import com.example.tutorservice.services.TutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TutorKafkaConsumer {

    public final TutorService tutorService;

    @KafkaListener(topics = {"${spring.kafka.custom.tutor-signup-topic}"}, containerFactory = "kafkaListenerJsonFactory",
            groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.custom.enable-listeners}")
    public void consumeTutorSignUpDTO(UserDTO userDTO) {
        log.info("Received Tutor SignUp {}", userDTO);
        TutorDTO tutorDTO = new TutorDTO();
        tutorDTO.setShortInfo(userDTO.getShortInfo());
        tutorDTO.setExpertise(userDTO.getExpertise());
        tutorDTO.setUserId(userDTO.getId());
        tutorDTO = tutorService.signupTutor(tutorDTO);
        log.info("Successfully Saved Tutor {}", tutorDTO);
    }
}
