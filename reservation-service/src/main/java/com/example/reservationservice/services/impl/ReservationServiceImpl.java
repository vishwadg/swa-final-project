package com.example.reservationservice.services.impl;

import com.example.commonsmodule.DTOs.ReservationDTO;
import com.example.commonsmodule.security.CommonSecurityUtils;
import com.example.reservationservice.entities.Reservation;
import com.example.reservationservice.repositories.ReservationRepository;
import com.example.reservationservice.services.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        reservationDTO.setTutorUserId(CommonSecurityUtils.getCurrentUserId().get());
        log.info("Reservation process started...");
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation.setReservationDate(LocalDate.now().toString());

        Reservation reservationRepo = reservationRepository.save(reservation);
        reservationDTO.setReservationId(reservationRepo.getReservationId());
        reservationDTO.setTutorRequirementId(reservationRepo.getTutorRequirementId());
        reservationDTO.setTutorRequirementTitle(reservationDTO.getTutorRequirementTitle());
        reservationDTO.setTutorRequirementDesc(reservationDTO.getTutorRequirementDesc());
        reservationDTO.setReservationDate(reservationRepo.getReservationDate());
        kafkaReservationTemplate.send(reservationTopic, reservationDTO);
        log.info("Success: Reservation data saved");
        return reservationDTO;
    }

    @Override
    public List<ReservationDTO> findAllByTutorRequirementId(String tutorRequirement) {
        List<Reservation> reservationList = reservationRepository.findReservationsByTutorRequirementId(tutorRequirement);
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

    @Override
    public List<ReservationDTO> findAllReservationByTutorUserId() {
        Long userId = CommonSecurityUtils.getCurrentUserId().get();
        return reservationRepository.findAllByTutorUserId(userId).stream().map(
                re -> modelMapper.map(re, ReservationDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> findAllReservationRequestByStudentUserId() {
        Long userId = CommonSecurityUtils.getCurrentUserId().get();
        return reservationRepository.findAllByStudentUserId(userId).stream().map(
                re -> modelMapper.map(re, ReservationDTO.class)
        ).collect(Collectors.toList());
    }


}
