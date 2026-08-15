package com.realestate.notification_service.dto;

import lombok.Data;

@Data
public class SmsRequestDto {
    private String phoneNumber;
    private String message;
}