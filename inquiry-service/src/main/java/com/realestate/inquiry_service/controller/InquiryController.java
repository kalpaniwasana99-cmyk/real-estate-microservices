package com.realestate.inquiry_service.controller;

import com.realestate.inquiry_service.model.Inquiry;
import com.realestate.inquiry_service.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inquiries")
@CrossOrigin(origins = "*")
public class InquiryController {

    @Autowired
    private InquiryRepository inquiryRepository;

    // 1. අලුත් Inquiry එකක් Save කිරීම (පරිපූර්ණ ආරක්ෂිත ක්‍රමය)
    @PostMapping
    public ResponseEntity<?> createInquiry(@RequestBody Inquiry inquiry) {
        try {
            if (inquiry.getInquiryDate() == null) {
                inquiry.setInquiryDate(LocalDateTime.now());
            }
            Inquiry savedInquiry = inquiryRepository.save(inquiry);
            return new ResponseEntity<>(savedInquiry, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error saving inquiry: " + e.getMessage());
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. සියලුම Inquiries ලබා ගැනීම
    @GetMapping
    public ResponseEntity<List<Inquiry>> getAllInquiries() {
        try {
            List<Inquiry> inquiries = inquiryRepository.findAll();
            return new ResponseEntity<>(inquiries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 3. By Property ID (Long ටයිප් එකට ගැළපෙන පරිදි නිවැරදි කරන ලදී)
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Inquiry>> getInquiriesByPropertyId(@PathVariable String propertyId) {
        try {
            List<Inquiry> inquiries = inquiryRepository.findByPropertyId(propertyId);
            return new ResponseEntity<>(inquiries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 4. Inquiry එකක් Update කිරීම
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInquiry(@PathVariable String id, @RequestBody Inquiry inquiryDetails) {
        try {
            Optional<Inquiry> optionalInquiry = inquiryRepository.findById(id);
            if (optionalInquiry.isPresent()) {
                Inquiry inquiry = optionalInquiry.get();
                if (inquiryDetails.getMessage() != null) inquiry.setMessage(inquiryDetails.getMessage());
                if (inquiryDetails.getCustomerName() != null) inquiry.setCustomerName(inquiryDetails.getCustomerName());
                if (inquiryDetails.getCustomerEmail() != null) inquiry.setCustomerEmail(inquiryDetails.getCustomerEmail());
                if (inquiryDetails.getPropertyId() != null) inquiry.setPropertyId(inquiryDetails.getPropertyId());
                
                Inquiry updatedInquiry = inquiryRepository.save(inquiry);
                return new ResponseEntity<>(updatedInquiry, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Inquiry not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 5. Inquiry එකක් Delete කිරීම
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteInquiry(@PathVariable String id) {
        try {
            if (inquiryRepository.existsById(id)) {
                inquiryRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}