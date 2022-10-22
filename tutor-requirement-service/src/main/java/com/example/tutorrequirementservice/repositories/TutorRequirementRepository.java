package com.example.tutorrequirementservice.repositories;

import com.example.tutorrequirementservice.entity.TutorRequirement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRequirementRepository extends MongoRepository<TutorRequirement, String> {
    List<TutorRequirement> findAllByStudentUserId(Long studentUserId);
}
