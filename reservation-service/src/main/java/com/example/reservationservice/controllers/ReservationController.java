package com.example.reservationservice.controllers;

import com.example.reservationservice.DTOs.ReservationDTO;
import com.example.reservationservice.repositories.ReservationRepository;
import com.example.reservationservice.services.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> reserve(@RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(reservationService.save(reservationDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findReservationById(@PathVariable String id) {
        return new ResponseEntity<>(reservationService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllReservation() {
        return new ResponseEntity<>(reservationService.findAll(), HttpStatus.OK);
    }
}
