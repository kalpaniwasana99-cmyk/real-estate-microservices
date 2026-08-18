package com.realestate.inquiry_service.repository;

import com.realestate.inquiry_service.model.Inquiry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends MongoRepository<Inquiry, String> {
    List<Inquiry> findByPropertyId(String propertyId);
}