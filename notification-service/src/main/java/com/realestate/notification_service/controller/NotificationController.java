package com.realestate.notification_service.controller;

import com.realestate.notification_service.dto.EmailRequestDto;
import com.realestate.notification_service.dto.ReplyRequestDto;
import com.realestate.notification_service.dto.SmsRequestDto;
import com.realestate.notification_service.entity.Notification;
import com.realestate.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notify")
@CrossOrigin(origins = "*") // CORS issue එක මගහැරීමට
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

    // Received Inbox (Read)
    @GetMapping("/inbox/received/{email}")
    public List<Notification> getReceivedInbox(@PathVariable String email) {
        return notificationService.getReceivedInbox(email);
    }

    // Sent Inbox (Read)
    @GetMapping("/inbox/sent/{email}")
    public List<Notification> getSentInbox(@PathVariable String email) {
        return notificationService.getSentInbox(email);
    }

    // Mark as Read (Update)
    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable String id) {
        return notificationService.markAsRead(id);
    }

    // Reply (Create)
    @PostMapping("/{id}/reply")
    public Notification reply(@PathVariable String id, @RequestBody ReplyRequestDto replyRequest) {
        return notificationService.replyToNotification(id, replyRequest);
    }

    // Delete (Delete)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        notificationService.deleteNotification(id);
    }
}