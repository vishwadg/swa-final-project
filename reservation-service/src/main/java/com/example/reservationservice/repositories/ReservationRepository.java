package com.example.reservationservice.repositories;

import com.example.reservationservice.entities.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findReservationsByTutorRequirementId(String tutorRequirementId);
    List<Reservation> findAllByTutorUserId(Long userId);
    List<Reservation> findAllByStudentUserId(Long userId);
}
