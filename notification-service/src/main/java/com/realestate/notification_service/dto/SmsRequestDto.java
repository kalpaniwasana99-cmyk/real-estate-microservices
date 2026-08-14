package com.realestate.notificationservice.dto;

import lombok.Data;

@Data
public class SmsRequestDto {
    private String phoneNumber;
    private String message;
}