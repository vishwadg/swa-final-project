package com.example.tutorrequirementservice.repositories;

import com.example.tutorrequirementservice.entity.TutorRequirement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRequirementRepository extends MongoRepository<TutorRequirement, String> {
}
