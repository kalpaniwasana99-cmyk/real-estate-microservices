 package com.realestate.notificationservice.service;

import com.realestate.notificationservice.dto.EmailRequestDto;
import com.realestate.notificationservice.dto.SmsRequestDto;
import com.realestate.notificationservice.entity.Notification;
import com.realestate.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    // Email Sending Logic
    public Notification sendEmail(EmailRequestDto emailRequest) {
        Notification notification = Notification.builder()
                .recipient(emailRequest.getToEmail())
                .subject(emailRequest.getSubject())
                .message(emailRequest.getBody())
                .type("EMAIL")
                .timestamp(LocalDateTime.now())
                .build();

        try {
            if (mailSender != null) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(emailRequest.getToEmail());
                message.setSubject(emailRequest.getSubject());
                message.setText(emailRequest.getBody());
                mailSender.send(message);
            }
            notification.setStatus("SENT");
        } catch (Exception e) {
            notification.setStatus("FAILED");
        }

        return notificationRepository.save(notification);
    }

    // SMS Sending Logic
    public Notification sendSms(SmsRequestDto smsRequest) {
        Notification notification = Notification.builder()
                .recipient(smsRequest.getPhoneNumber())
                .subject("SMS Notification")
                .message(smsRequest.getMessage())
                .type("SMS")
                .timestamp(LocalDateTime.now())
                .build();

        try {
            System.out.println("Sending SMS to " + smsRequest.getPhoneNumber() + ": " + smsRequest.getMessage());
            notification.setStatus("SENT");
        } catch (Exception e) {
            notification.setStatus("FAILED");
        }

        return notificationRepository.save(notification);
    }

    // Get History
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByRecipient(String recipient) {
        return notificationRepository.findByRecipient(recipient);
    }
}