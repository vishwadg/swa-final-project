package com.tutor.notificationservice.service;

import com.tutor.notificationservice.entity.Response;
import com.tutor.notificationservice.entity.TutorEmail;

import java.util.Optional;

public interface TutorEmailNotificationService {
    Response sendEmail(TutorEmail email);
    Optional<TutorEmail> findAllById(Long id);
}
