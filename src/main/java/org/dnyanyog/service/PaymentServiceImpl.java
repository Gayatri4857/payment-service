package org.dnyanyog.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.annotation.PostConstruct;
import org.dnyanyog.dto.PaymentRequestDTO;
import org.dnyanyog.dto.PaymentResponseDTO;
import org.dnyanyog.entity.Payment;
import org.dnyanyog.repo.PaymentRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl {

  private RazorpayClient razorpayClient;

  @Autowired private PaymentRepository paymentRepository;

  @Value("${razorpay.key}")
  private String apiKey;

  @Value("${razorpay.secret}")
  private String apiSecret;

  @PostConstruct
  public void init() throws RazorpayException {
    this.razorpayClient = new RazorpayClient(apiKey, apiSecret);
  }

  public PaymentResponseDTO createOrder(PaymentRequestDTO paymentRequestDTO)
      throws RazorpayException {
    JSONObject orderRequest = new JSONObject();
    orderRequest.put("amount", paymentRequestDTO.getAmount());
    orderRequest.put("currency", paymentRequestDTO.getCurrency());
    orderRequest.put("receipt", paymentRequestDTO.getReceipt());

    Order order = razorpayClient.orders.create(orderRequest);

    Payment payment = new Payment();
    payment.setOrderId(order.get("id"));
    payment.setAmount(Double.valueOf(paymentRequestDTO.getAmount()) / 100);
    payment.setStatus("CREATED");
    paymentRepository.save(payment);

    PaymentResponseDTO responseDTO = new PaymentResponseDTO();
    responseDTO.setOrderId(order.get("id"));
    responseDTO.setStatus("CREATED");
    return responseDTO;
  }

  public void updatePaymentStatus(String paymentId, String orderId, String status) {
    Payment payment = paymentRepository.findByOrderId(orderId);
    payment.setPaymentId(paymentId);
    payment.setStatus(status);
    paymentRepository.save(payment);
  }
}
