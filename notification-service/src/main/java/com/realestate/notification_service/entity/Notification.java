package com.realestate.notification_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient; // Email or Phone Number
    private String subject;   // Email Subject 
    
    @Column(length = 1000)
    private String message;   // Notification 
    
    private String type;      // EMAIL or SMS
    private String status;    // SENT or FAILED

    private LocalDateTime timestamp;
}