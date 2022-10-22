package com.example.reservationservice.services.impl;

import com.example.reservationservice.DTOs.ReservationDTO;
import com.example.reservationservice.entities.Reservation;
import com.example.reservationservice.repositories.ReservationRepository;
import com.example.reservationservice.services.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    @Value("${spring.kafka.custom.reservation-topic}")
    private String reservationTopic;
    @Value("${spring.kafka.custom.reservation-approved-topic}")
    private String reservationApprovedTopic;
    private final KafkaTemplate<String, ReservationDTO> kafkaReservationTemplate;

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
    public List<ReservationDTO> findAllByTutorRequirementId(String tutorRequirement) {
        List<Reservation> reservationList = reservationRepository.findAllByTutorRequirementId(tutorRequirement);
        if (reservationList.isEmpty()) {
            log.info("Failure: Reservations are not found in the system");
            throw new RuntimeException("Sorry, reservations are not found in the system");
        }
        List<ReservationDTO> reservationDTOList = reservationList.stream().map(
                reservation -> modelMapper.map(reservation, ReservationDTO.class)
        ).toList();
        log.info("Reservation List sent for tutorRequirementId: {}", tutorRequirement);
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

    @Override
    @Transactional
    public ReservationDTO updateReservationStatus(ReservationDTO reservationDTO) {
        log.info("Reservation to be approved of ID {}", reservationDTO.getReservationId());
        Reservation reservation = reservationRepository.findById(reservationDTO.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found!"));
        reservation.setReservationStatus(true);
        reservationRepository.save(reservation);
        log.info("Reservation approved of ID {}", reservationDTO.getReservationId());

        log.info("Reservation approved event to be sent of ID {}", reservationDTO.getReservationId());
        kafkaReservationTemplate.send(reservationApprovedTopic, reservationDTO);
        log.info("Reservation approved event sent of ID {}", reservationDTO.getReservationId());

        return modelMapper.map(reservation, ReservationDTO.class);
    }
}
