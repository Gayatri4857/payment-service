package org.dnyanyog.service;

import com.razorpay.RazorpayException;
import org.dnyanyog.dto.PaymentRequestDTO;
import org.dnyanyog.dto.PaymentResponseDTO;

public interface PaymentService {
  public PaymentResponseDTO createOrder(PaymentRequestDTO paymentRequestDTO)
      throws RazorpayException;

  public void updatePaymentStatus(String paymentId, String orderId, String status);
}
