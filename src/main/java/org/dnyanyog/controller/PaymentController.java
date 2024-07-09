package org.dnyanyog.controller;

import org.dnyanyog.dto.PaymentRequestDTO;
import org.dnyanyog.dto.PaymentResponseDTO;
import org.dnyanyog.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

  @Autowired private PaymentServiceImpl paymentService;

  @PostMapping("/api/payment/create")
  public PaymentResponseDTO createOrder(@RequestBody PaymentRequestDTO paymentRequestDTO)
      throws Exception {
    return paymentService.createOrder(paymentRequestDTO);
  }

  @PostMapping("/api/payment/update")
  public void updatePaymentStatus(
      @RequestParam String paymentId, @RequestParam String orderId, @RequestParam String status) {
    paymentService.updatePaymentStatus(paymentId, orderId, status);
  }
}
