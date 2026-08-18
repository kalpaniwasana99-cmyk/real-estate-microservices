package com.realestate.notification_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private String senderEmail;    
    private String recipient;      
    private String subject;
    private String message;
    private String type;           
    private String status;         
    private boolean read;        
    private LocalDateTime timestamp;

    public Notification() {}

    public Notification(String id, String senderEmail, String recipient, String subject, String message, String type, String status, boolean read, LocalDateTime timestamp) {
        this.id = id;
        this.senderEmail = senderEmail;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.type = type;
        this.status = status;
        this.read = read;
        this.timestamp = timestamp;
    }

    // Builder Pattern implementation manually to avoid any Lombok issues
    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

    public static class NotificationBuilder {
        private String id;
        private String senderEmail;
        private String recipient;
        private String subject;
        private String message;
        private String type;
        private String status;
        private boolean read;
        private LocalDateTime timestamp;

        public NotificationBuilder id(String id) { this.id = id; return this; }
        public NotificationBuilder senderEmail(String senderEmail) { this.senderEmail = senderEmail; return this; }
        public NotificationBuilder recipient(String recipient) { this.recipient = recipient; return this; }
        public NotificationBuilder subject(String subject) { this.subject = subject; return this; }
        public NotificationBuilder message(String message) { this.message = message; return this; }
        public NotificationBuilder type(String type) { this.type = type; return this; }
        public NotificationBuilder status(String status) { this.status = status; return this; }
        public NotificationBuilder read(boolean read) { this.read = read; return this; }
        public NotificationBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public Notification build() {
            return new Notification(id, senderEmail, recipient, subject, message, type, status, read, timestamp);
        }
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}