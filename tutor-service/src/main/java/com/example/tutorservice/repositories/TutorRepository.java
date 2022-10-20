package com.example.tutorservice.repositories;

import com.example.tutorservice.entities.Tutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends MongoRepository<Tutor, String> {
}
