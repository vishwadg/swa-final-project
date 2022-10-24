package com.example.reservationservice.controllers;

import com.example.commonsmodule.DTOs.ReservationDTO;
import com.example.reservationservice.services.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reservation")
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_TUTOR')")
    public ResponseEntity<?> reserve(@RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(reservationService.save(reservationDTO), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> updateReservationStatus(@RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(reservationService.updateReservationStatus(reservationDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findReservationById(@PathVariable String id) {
        return new ResponseEntity<>(reservationService.findById(id), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/tutorRequirementId/{tutorRequirement}")
    public ResponseEntity<?> findAllReservationByTutorRequirementId(@PathVariable String tutorRequirement) {
        return new ResponseEntity<>(reservationService.findAllByTutorRequirementId(tutorRequirement), HttpStatus.OK);
    }
}
