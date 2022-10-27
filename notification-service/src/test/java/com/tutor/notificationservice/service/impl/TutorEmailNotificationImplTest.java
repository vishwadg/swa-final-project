package com.tutor.notificationservice.service.impl;

import com.tutor.notificationservice.entity.Email;
import com.tutor.notificationservice.entity.Response;
import com.tutor.notificationservice.service.TutorEmailNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest()
class TutorEmailNotificationImplTest {
    @Autowired
    private TutorEmailNotificationService notificationService;

    @MockBean
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void sendEmail() {
        Email email = new Email("eme2119@gmail.com", "Hi", "This is from tutor finder");
        Response response=notificationService.sendEmail(email);
        assertThat(response.getCode())
                .isEqualTo(0);
    }

}
