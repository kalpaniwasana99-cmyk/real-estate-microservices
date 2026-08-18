package com.realestate.notification_service.service;

import com.realestate.notification_service.dto.EmailRequestDto;
import com.realestate.notification_service.dto.ReplyRequestDto;
import com.realestate.notification_service.dto.SmsRequestDto;
import com.realestate.notification_service.entity.Notification;
import com.realestate.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Notification sendEmail(EmailRequestDto request) {
        String status = "SENT";
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(request.getSenderEmail());
            mail.setTo(request.getToEmail());
            mail.setSubject(request.getSubject());
            mail.setText(request.getBody());
            mailSender.send(mail);
        } catch (Exception e) {
            status = "FAILED";
        }

        Notification notification = Notification.builder()
                .senderEmail(request.getSenderEmail())
                .recipient(request.getToEmail())
                .subject(request.getSubject())
                .message(request.getBody())
                .type("EMAIL")
                .status(status)
                .read(false)
                .timestamp(LocalDateTime.now())
                .build();

        return notificationRepository.save(notification);
    }

    public Notification sendSms(SmsRequestDto request) {
        System.out.println("Sending SMS to " + request.getPhoneNumber() + ": " + request.getMessage());

        Notification notification = Notification.builder()
                .senderEmail(request.getSenderEmail())
                .recipient(request.getPhoneNumber())
                .subject("SMS Notification")
                .message(request.getMessage())
                .type("SMS")
                .status("SENT")
                .read(false)
                .timestamp(LocalDateTime.now())
                .build();

        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByRecipient(String recipient) {
        return notificationRepository.findByRecipient(recipient);
    }

    public List<Notification> getReceivedInbox(String email) {
        return notificationRepository.findByRecipientOrderByTimestampDesc(email);
    }

    public List<Notification> getSentInbox(String email) {
        return notificationRepository.findBySenderEmailOrderByTimestampDesc(email);
    }

    public Notification markAsRead(String id) {
        Optional<Notification> optional = notificationRepository.findById(id);
        if (optional.isPresent()) {
            Notification notification = optional.get();
            notification.setRead(true);
            return notificationRepository.save(notification);
        }
        throw new RuntimeException("Notification not found with id: " + id);
    }

    public Notification replyToNotification(String id, ReplyRequestDto replyRequest) {
        Optional<Notification> optional = notificationRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Notification not found with id: " + id);
        }

        Notification original = optional.get();

        String status = "SENT";
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(replyRequest.getSenderEmail());
            mail.setTo(original.getSenderEmail());
            mail.setSubject("Re: " + original.getSubject());
            mail.setText(replyRequest.getMessage());
            mailSender.send(mail);
        } catch (Exception e) {
            status = "FAILED";
        }

        Notification reply = Notification.builder()
                .senderEmail(replyRequest.getSenderEmail())
                .recipient(original.getSenderEmail())
                .subject("Re: " + original.getSubject())
                .message(replyRequest.getMessage())
                .type("EMAIL")
                .status(status)
                .read(false)
                .timestamp(LocalDateTime.now())
                .build();

        return notificationRepository.save(reply);
    }

    public void deleteNotification(String id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }
}