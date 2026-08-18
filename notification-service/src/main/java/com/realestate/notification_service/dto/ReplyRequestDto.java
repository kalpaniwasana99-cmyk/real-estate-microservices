package com.realestate.notification_service.dto;

public class ReplyRequestDto {
    private String senderEmail;    
    private String message;

    public ReplyRequestDto() {}

    public ReplyRequestDto(String senderEmail, String message) {
        this.senderEmail = senderEmail;
        this.message = message;
    }

    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}