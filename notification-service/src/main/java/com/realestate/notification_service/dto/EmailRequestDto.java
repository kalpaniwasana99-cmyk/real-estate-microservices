package com.realestate.notification_service.dto;

public class EmailRequestDto {
    private String senderEmail; 
    private String toEmail;
    private String subject;
    private String body;

    public EmailRequestDto() {}

    public EmailRequestDto(String senderEmail, String toEmail, String subject, String body) {
        this.senderEmail = senderEmail;
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
    }

    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }

    public String getToEmail() { return toEmail; }
    public void setToEmail(String toEmail) { this.toEmail = toEmail; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}