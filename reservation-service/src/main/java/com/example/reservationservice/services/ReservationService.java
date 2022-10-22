package com.example.reservationservice.services;

import com.example.reservationservice.DTOs.ReservationDTO;


import java.util.List;


public interface ReservationService {
    ReservationDTO save(ReservationDTO reservationDTO);

    List<ReservationDTO> findAllByTutorRequirementId(String tutorRequirement);

    ReservationDTO findById(String id);

    ReservationDTO updateReservationStatus(ReservationDTO reservationDTO);
}
