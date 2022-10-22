package com.example.tutorrequirementservice.controllers;

import com.example.commonsmodule.DTOs.TutorRequirementDTO;
import com.example.tutorrequirementservice.services.TutorRequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutor-requirements")
@RequiredArgsConstructor
public class TutorRequirementController {

    private final TutorRequirementService tutorRequirementService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public TutorRequirementDTO save(@RequestBody TutorRequirementDTO payload){
        return tutorRequirementService.save(payload);
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public List<TutorRequirementDTO> findAllByStudentUserId(){
        return tutorRequirementService.findAllByStudentUserId();
    }

    @GetMapping
    public List<TutorRequirementDTO> findAll(){
        return tutorRequirementService.findAll();
    }

    @GetMapping("/{id}")
    public TutorRequirementDTO findOne(@PathVariable String id){
        return tutorRequirementService.findOne(id);
    }

}
