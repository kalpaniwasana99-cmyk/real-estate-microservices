package com.realestate.inquiry_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "inquiries")
public class Inquiry {

    @Id
    private String id;

    private String propertyId; // Long වෙනුවට String ලෙස වෙනස් කරන ලදී (Frontend එකෙන් එන IDs වලට ගැළපීමට)

    private String customerName;

    private String customerEmail;

    private String message;

    private LocalDateTime inquiryDate = LocalDateTime.now(); 

    public Inquiry() {
    }

    public Inquiry(String propertyId, String customerName, String customerEmail, String message) {
        this.propertyId = propertyId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.message = message;
        this.inquiryDate = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPropertyId() { return propertyId; }
    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getInquiryDate() { return inquiryDate; }
    public void setInquiryDate(LocalDateTime inquiryDate) { this.inquiryDate = inquiryDate; }
}