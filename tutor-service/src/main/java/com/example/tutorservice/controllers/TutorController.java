package com.example.tutorservice.controllers;
import com.example.commonsmodule.DTOs.TutorDTO;
import com.example.tutorservice.services.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutors")
public class TutorController {
    @Autowired
    TutorService tutorService;

    @PostMapping
    ResponseEntity<?> signupTutor(@RequestBody TutorDTO tutorDTO) {
        return new ResponseEntity<>(tutorService.signupTutor(tutorDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTutorById(@PathVariable String id) {
        return new ResponseEntity<>(tutorService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllTutor() {
        return new ResponseEntity<>(tutorService.findAll(), HttpStatus.OK);
    }

    private TutorDTO tutorServiceFallback(TutorDTO tutorDTO, Throwable throwable) {
        return tutorService.signupTutor(tutorDTO);
    }
}
