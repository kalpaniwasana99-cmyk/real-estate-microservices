package com.realestate.inquiry_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "inquiries")
public class Inquiry {

    @Id
    private String id; // MongoDB සඳහා ID එක String බවට වෙනස් කළා

    private Long propertyId; // Hasindu ගේ Property Service එකේ අදාළ දේපළේ ID එක (ඒක Long නිසා වෙනස් කළේ නෑ)

    private String customerName;

    private String customerEmail;

    private String message;

    // Object එක හැදෙන වෙලාවෙම ස්වයංක්‍රීයව වෙලාව සේව් වීම සඳහා
    private LocalDateTime inquiryDate = LocalDateTime.now(); 

    // Default Constructor
    public Inquiry() {
    }

    // Parameterized Constructor
    public Inquiry(Long propertyId, String customerName, String customerEmail, String message) {
        this.propertyId = propertyId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.message = message;
        this.inquiryDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getInquiryDate() { return inquiryDate; }
    public void setInquiryDate(LocalDateTime inquiryDate) { this.inquiryDate = inquiryDate; }
}