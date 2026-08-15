 package com.realestate.notification_service.controller;

import com.realestate.notification_service.dto.EmailRequestDto;
import com.realestate.notification_service.dto.SmsRequestDto;
import com.realestate.notification_service.entity.Notification;
import com.realestate.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/email")
    public Notification sendEmail(@RequestBody EmailRequestDto request) {
        return notificationService.sendEmail(request);
    }

    @PostMapping("/sms")
    public Notification sendSms(@RequestBody SmsRequestDto request) {
        return notificationService.sendSms(request);
    }

    @GetMapping("/history")
    public List<Notification> getAllHistory() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/history/{recipient}")
    public List<Notification> getHistoryByRecipient(@PathVariable String recipient) {
        return notificationService.getNotificationsByRecipient(recipient);
    }
}