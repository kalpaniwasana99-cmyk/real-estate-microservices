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
@CrossOrigin(origins = "*") // CORS Error එක මඟහරවා ගැනීමට මෙය අත්‍යවශ්‍යයි
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

    // 3. By Property ID - අදාළ Property එකට අදාළ Inquiries ලබා ගැනීම (String ලෙස නිවැරදි කරන ලදී)
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Inquiry>> getInquiriesByPropertyId(@PathVariable String propertyId) {
        List<Inquiry> inquiries = inquiryRepository.findByPropertyId(propertyId);
        return new ResponseEntity<>(inquiries, HttpStatus.OK);
    }

    // 4. Inquiry එකක් Update කිරීම (Edit)
    @PutMapping("/{id}")
    public ResponseEntity<Inquiry> updateInquiry(@PathVariable String id, @RequestBody Inquiry inquiryDetails) {
        return inquiryRepository.findById(id)
                .map(inquiry -> {
                    inquiry.setMessage(inquiryDetails.getMessage());
                    inquiry.setCustomerName(inquiryDetails.getCustomerName());
                    inquiry.setCustomerEmail(inquiryDetails.getCustomerEmail());
                    Inquiry updatedInquiry = inquiryRepository.save(inquiry);
                    return new ResponseEntity<>(updatedInquiry, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 5. Inquiry එකක් Delete කිරීම
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteInquiry(@PathVariable String id) {
        try {
            inquiryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}