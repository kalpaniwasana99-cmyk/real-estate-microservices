package com.realestate.inquiry_service.repository;

import com.realestate.inquiry_service.model.Inquiry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends MongoRepository<Inquiry, String> {

    // නිශ්චිත Property ID එකකට අදාළ සියලුම විමසීම් (Inquiries) සෙවීම සඳහා
    List<Inquiry> findByPropertyId(Long propertyId);
}