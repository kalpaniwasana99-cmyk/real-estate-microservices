package com.realestate.notification_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String senderEmail;    
    private String recipient;      
    private String subject;
    private String message;
    private String type;           
    private String status;         

    @JsonProperty("read") // Frontend JS එකේ n.read ලෙස කෙලින්ම ගැලපීමට
    private boolean read;        

    private LocalDateTime timestamp;
}