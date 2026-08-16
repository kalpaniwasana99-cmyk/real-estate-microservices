package com.realestate.paymentservice.controller;

import com.realestate.paymentservice.model.Payment;
import com.realestate.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    // 1. නව ගෙවීමක් සිදු කිරීමට (POST: /api/payments/process)
    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
        payment.setPaymentStatus("SUCCESS");
        Payment savedPayment = paymentRepository.save(payment);
        return ResponseEntity.ok(savedPayment);
    }

    // 2. සියලුම ගෙවීම් ඉතිහාසය ලබා ගැනීමට (GET: /api/payments/history)
    @GetMapping("/history")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return ResponseEntity.ok(payments);
    }

    // 3. නිශ්චිත User කෙනෙකුට අදාළ ගෙවීම් බැලීමට (GET: /api/payments/history/{userId})
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUser(@PathVariable String userId) {
        List<Payment> payments = paymentRepository.findByUserId(userId);
        return ResponseEntity.ok(payments);
    }

    // 4. නිශ්චිත Payment ID එකක් මඟින් විස්තර බැලීමට (GET: /api/payments/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}