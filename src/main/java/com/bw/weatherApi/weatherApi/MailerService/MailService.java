package com.bw.weatherApi.weatherApi.MailerService;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendSimpleMail(String to, String subject, String message);
    void sendEmail(MailDto mailDto, String templateName);
}
