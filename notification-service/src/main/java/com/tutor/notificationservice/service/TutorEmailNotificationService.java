package com.tutor.notificationservice.service;

import com.tutor.notificationservice.entity.Response;
import com.tutor.notificationservice.entity.TutorEmail;

public interface TutorEmailNotificationService {
    Response sendEmail(TutorEmail email);
}
