package com.bw.weatherApi.weatherApi.MailerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class MailServiceImpl implements MailService {

    @Autowired
    public JavaMailSender mailSender;

    public static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);


    @Override
    @Async("mailServiceTaskExecutor")
    public void sendSimpleMail(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("tadenekan@byteworks.com.ng");
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
            logger.info("mail sent to " + to);
        }catch (Exception ex){
            ex.printStackTrace();
            logger.info("failed to send email");

        }


    }

    @Override
    public void sendEmail(MailDto mailDto, String templateName) {

    }
}
