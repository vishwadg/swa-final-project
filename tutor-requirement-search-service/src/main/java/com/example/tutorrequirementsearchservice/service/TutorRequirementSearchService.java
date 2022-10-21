package com.example.tutorrequirementsearchservice.service;


import com.example.commonsmodule.DTOs.TutorRequirementDTO;

import java.util.List;

public interface TutorRequirementSearchService {
    TutorRequirementDTO save(TutorRequirementDTO tutorRequirementDTO);

    List<TutorRequirementDTO> findAllByTutorRequirement(String tutorRequirement);

    TutorRequirementDTO findOne(String id);

    TutorRequirementDTO update(TutorRequirementDTO tutorRequirementDTO);

    void deleteById(String id);

}
