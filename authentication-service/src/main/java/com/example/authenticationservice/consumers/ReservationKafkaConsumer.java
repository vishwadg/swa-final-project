package com.example.authenticationservice.consumers;

import com.example.authenticationservice.services.AuthenticationService;
import com.example.commonsmodule.DTOs.ReservationDTO;
import com.example.commonsmodule.DTOs.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationKafkaConsumer {
    public final AuthenticationService authenticationService;

    @KafkaListener(topics = {"${spring.kafka.custom.reservation-topic}"}, containerFactory = "kafkaListenerJsonFactory",
            groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.custom.enable-listeners}")
    public void consumeReservationDTO(ReservationDTO reservationDTO) {
        log.info("Received ReservationDTO {}", reservationDTO);
        UserDTO userDTO = authenticationService.getUserByUserId(reservationDTO.getTutorUserId());
        log.info("Successfully fetched UserDTO {}", userDTO);
    }
}
