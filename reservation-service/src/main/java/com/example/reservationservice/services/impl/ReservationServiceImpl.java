package com.example.reservationservice.services.impl;

import com.example.reservationservice.DTOs.ReservationDTO;
import com.example.reservationservice.entities.Reservation;
import com.example.reservationservice.repositories.ReservationRepository;
import com.example.reservationservice.services.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    @Value("${spring.kafka.custom.reservation-topic}")
    private String reservationTopic;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private KafkaTemplate<String, ReservationDTO> kafkaReservationTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReservationDTO save(ReservationDTO reservationDTO) {
        log.info("Reservation process started...");
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        Reservation reservationRepo = reservationRepository.save(reservation);
        reservationDTO.setReservationId(reservationRepo.getReservationId());
        kafkaReservationTemplate.send(reservationTopic, reservationDTO);
        log.info("Success: Reservation data saved");
        return reservationDTO;
    }

    @Override
    public List<ReservationDTO> findAll() {
        List<Reservation> reservationList = reservationRepository.findAll();
        if (reservationList.isEmpty()) {
            log.info("Failure: Reservations are not found in the system");
            throw new RuntimeException("Sorry, reservations are not found in the system");
        }
        List<ReservationDTO> reservationDTOList = reservationList.stream().map(
                reservation -> modelMapper.map(reservation, ReservationDTO.class)
            ).toList();

        log.info("Success: Reservation list saved");
        return reservationDTOList;
    }

    @Override
    public ReservationDTO findById(String id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        Reservation reservation = reservationOptional.orElseThrow();
        if (reservation == null) {
            log.error("Failure: Reservation not found with id {}", id);
            throw new RuntimeException("Sorry, reservation not found in the system");
        }
        ReservationDTO reservationDTO = modelMapper.map(reservation, ReservationDTO.class);
        log.info("Success: Reservation found with id{}", id);
        return reservationDTO;
    }
}
