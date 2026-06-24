package com.mobileshop.controller;

import com.mobileshop.dto.PaymentOrderRequestDTO;
import com.mobileshop.dto.PaymentOrderResponseDTO;
import com.mobileshop.dto.PaymentVerifyRequestDTO;
import com.mobileshop.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<PaymentOrderResponseDTO> createPaymentOrder(
            @Valid @RequestBody PaymentOrderRequestDTO dto) {
        return ResponseEntity.ok(paymentService.createPaymentOrder(dto));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(
            @Valid @RequestBody PaymentVerifyRequestDTO dto) {
        return ResponseEntity.ok(paymentService.verifyPayment(dto));
    }

}
