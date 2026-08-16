package com.realestate.inquiry_service.controller;

import com.realestate.inquiry_service.model.Inquiry;
import com.realestate.inquiry_service.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    @Autowired
    private InquiryRepository inquiryRepository;

    // 1. අලුත් Inquiry එකක් Save කිරීම
    @PostMapping
    public ResponseEntity<Inquiry> createInquiry(@RequestBody Inquiry inquiry) {
        Inquiry savedInquiry = inquiryRepository.save(inquiry);
        return new ResponseEntity<>(savedInquiry, HttpStatus.CREATED);
    }

    // 2. සියලුම Inquiries ලබා ගැනීම
    @GetMapping
    public ResponseEntity<List<Inquiry>> getAllInquiries() {
        List<Inquiry> inquiries = inquiryRepository.findAll();
        return new ResponseEntity<>(inquiries, HttpStatus.OK);
    }

    // 3. By Property ID - අදාළ Property එකට අදාළ Inquiries පමණක් ලබා ගැනීම
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Inquiry>> getInquiriesByPropertyId(@PathVariable Long propertyId) {
        List<Inquiry> inquiries = inquiryRepository.findByPropertyId(propertyId);
        return new ResponseEntity<>(inquiries, HttpStatus.OK);
    }
}