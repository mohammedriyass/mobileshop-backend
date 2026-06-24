package com.mobileshop.service;

import com.mobileshop.dto.PaymentOrderRequestDTO;
import com.mobileshop.dto.PaymentOrderResponseDTO;
import com.mobileshop.dto.PaymentVerifyRequestDTO;
import com.mobileshop.entity.Order;
import com.mobileshop.repository.OrderRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Formatter;

@Service
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Autowired
    private OrderRepository orderRepository;

    public PaymentOrderResponseDTO createPaymentOrder(PaymentOrderRequestDTO dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("order Not Found"));

        try {
            RazorpayClient client = new RazorpayClient(keyId, keySecret);

            int amountInPaise = order.getTotalAmount().multiply(BigDecimal.valueOf(100))
                    .intValue();

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_" + order.getId());

            com.razorpay.Order razorpayOrder = client.orders.create(orderRequest);

            order.setRazorpayOrderId(razorpayOrder.get("id"));
            orderRepository.save(order);

            PaymentOrderResponseDTO response = new PaymentOrderResponseDTO();
            response.setRazorpayOrderId(razorpayOrder.get("id"));
            response.setAmount(order.getTotalAmount());
            response.setCurrency("INR");
            response.setKeyId(keyId);
            response.setCustomerName(order.getCustomerName());
            response.setCustomerPhone(order.getCustomerPhone());
            response.setCustomerEmail("customer@mobileshop.com");

            return response;

        } catch (RazorpayException e) {
            throw new RuntimeException("Payment order creation failed: " + e.getMessage());
        }

    }

    public String verifyPayment(PaymentVerifyRequestDTO dto) {
        try {

            String payload = dto.getRazorpayOrderId() + "|" + dto.getRazorpayPaymentId();
            String generatedSignature = generateHmacSha256(payload, keySecret);

            if (generatedSignature.equals(dto.getRazorpayOrderId())) {
                Order order = orderRepository.findById(dto.getOrderId())
                        .orElseThrow(() -> new RuntimeException("Order not found!"));

                order.setPaymentStatus("Paid");
                order.setOrderStatus("CONFIRMED");
                order.setRazorpayOrderId(dto.getRazorpayOrderId());
                orderRepository.save(order);

                return "Payment verified successfully";

            } else {
                throw new RuntimeException("Payment verification failed! Invalid signature.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Payment verification error: " + e.getMessage());
        }
    }

    private String generateHmacSha256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
