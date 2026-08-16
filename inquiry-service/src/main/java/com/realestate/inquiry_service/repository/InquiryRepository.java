package com.realestate.inquiry_service.repository;

import com.realestate.inquiry_service.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    // නිශ්චිත Property ID එකකට අදාළ සියලුම විමසීම් (Inquiries) සෙවීම සඳහා
    List<Inquiry> findByPropertyId(Long propertyId);
}