package com.realestate.notification_service.controller;

import com.realestate.notification_service.dto.EmailRequestDto;
import com.realestate.notification_service.dto.ReplyRequestDto;
import com.realestate.notification_service.dto.SmsRequestDto;
import com.realestate.notification_service.entity.Notification;
import com.realestate.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notify")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/email")
    public ResponseEntity<Notification> sendEmail(@RequestBody EmailRequestDto request) {
        return ResponseEntity.ok(notificationService.sendEmail(request));
    }

    @PostMapping("/sms")
    public ResponseEntity<Notification> sendSms(@RequestBody SmsRequestDto request) {
        return ResponseEntity.ok(notificationService.sendSms(request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/inbox/received/{email}")
    public ResponseEntity<List<Notification>> getReceivedInbox(@PathVariable String email) {
        return ResponseEntity.ok(notificationService.getReceivedInbox(email));
    }

    @GetMapping("/inbox/sent/{email}")
    public ResponseEntity<List<Notification>> getSentInbox(@PathVariable String email) {
        return ResponseEntity.ok(notificationService.getSentInbox(email));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable String id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<Notification> replyToNotification(@PathVariable String id, @RequestBody ReplyRequestDto replyRequest) {
        return ResponseEntity.ok(notificationService.replyToNotification(id, replyRequest));
    }
}