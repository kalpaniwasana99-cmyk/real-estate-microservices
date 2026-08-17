package com.realestate.notification_service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    private String id;

    private String recipient; // Email or Phone Number
    private String subject;
    private String message;
    private String type;      // EMAIL or SMS
    private String status;    // SENT or FAILED
    private LocalDateTime timestamp;
}