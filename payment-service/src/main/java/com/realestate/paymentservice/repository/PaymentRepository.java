package com.realestate.paymentservice.repository;

import com.realestate.paymentservice.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
    
    // User ID එකට අදාළ ගෙවීම් ඉතිහාසය ලබා ගැනීමට
    List<Payment> findByUserId(String userId);
}