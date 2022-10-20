package com.example.tutorrequirementservice.controllers;

import com.example.tutorrequirementservice.entity.DTOs.TutorRequirementDTO;
import com.example.tutorrequirementservice.services.TutorRequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutor-requirements")
@RequiredArgsConstructor
public class TutorRequirementController {

    private final TutorRequirementService tutorRequirementService;

    @PostMapping
    public TutorRequirementDTO save(@RequestBody TutorRequirementDTO payload){
        return tutorRequirementService.save(payload);
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
