package com.example.tutorrequirementsearchservice.controller;

import com.example.tutorrequirementsearchservice.service.TutorRequirementSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/search")
public class SearchController {
    @Autowired
    TutorRequirementSearchService tutorRequirementSearchService;

    @GetMapping
    public ResponseEntity<?> findAllByTutorRequirement(@RequestParam(value = "tutorRequirement", required = false) String tutorRequirement) {
        log.info("SearchController: find all by tutor requirement");
        return new ResponseEntity<>(tutorRequirementSearchService.findAllByTutorRequirement(tutorRequirement), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByTutorRequirementId(@PathVariable String id) {
        log.info("SearchController: find by tutor requirement id ");
        return new ResponseEntity<>(tutorRequirementSearchService.findOne(id), HttpStatus.OK);
    }

}
