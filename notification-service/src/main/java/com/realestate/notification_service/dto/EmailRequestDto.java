package com.realestate.notification_service.dto;

import lombok.Data;

@Data
public class EmailRequestDto {
    private String senderEmail; 
    private String toEmail;
    private String subject;
    private String body;
}