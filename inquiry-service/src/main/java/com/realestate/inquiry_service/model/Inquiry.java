package com.realestate.inquiry_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inquiries")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long propertyId; // Hasindu ගේ Property Service එකේ අදාළ දේපළේ ID එක

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerEmail;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime inquiryDate;

    // Default Constructor
    public Inquiry() {
    }

    // Parameterized Constructor
    public Inquiry(Long propertyId, String customerName, String customerEmail, String message) {
        this.propertyId = propertyId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.message = message;
    }

    // Record දාන මොහොතේ ස්වයංක්‍රීයව වෙලාව සේව් වීම සඳහා
    @PrePersist
    protected void onCreate() {
        this.inquiryDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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