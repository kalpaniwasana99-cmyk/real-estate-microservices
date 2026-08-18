package com.realestate.notification_service.dto;

import lombok.Data;

@Data
public class ReplyRequestDto {
    private String senderEmail;   
    private String message;
}