package com.tutor.notificationservice.service;

import com.tutor.notificationservice.entity.Response;
import com.tutor.notificationservice.entity.Email;

public interface TutorEmailNotificationService {
    Response sendEmail(Email email);
}
