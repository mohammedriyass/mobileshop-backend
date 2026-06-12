package com.mobileshop.service;

import com.mobileshop.dto.OrderRequestDTO;
import com.mobileshop.dto.OrderResponseDTO;
import com.mobileshop.entity.Mobile;
import com.mobileshop.entity.Order;
import com.mobileshop.repository.MobileRepository;
import com.mobileshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MobileRepository mobileRepository;

    public OrderResponseDTO placeOrder(OrderRequestDTO dto) {

        Mobile mobile = mobileRepository.findById(dto.getMobileId())
                .orElseThrow(() -> new RuntimeException("Mobile Not Found!"));

        if (!mobile.getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("Mobile is already sold!");

        }

        Order order = new Order();
        order.setMobile(mobile);
        order.setCustomerName(dto.getCustomerName());
        order.setCustomerPhone(dto.getCustomerPhone());
        order.setCustomerAddress(dto.getCustomerAddress());
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setTotalAmount(mobile.getSellingPrice());

        // If COD → mark mobile as sold immediately
        if (dto.getPaymentMethod().equals("COD")) {
            order.setOrderStatus("CONFIRMED");
            mobile.setStatus("SOLD");
            mobileRepository.save(mobile);
        }

        Order saved = orderRepository.save(order);
        return mapToResponse(saved);

    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
        return mapToResponse(order);
    }

    public List<OrderResponseDTO> getPendingOrders() {
        return orderRepository.findByOrderStatusOrderByCreatedAtDesc("PENDING")
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        order.setOrderStatus(status);

        if (status.equals("DELIVERED")) {
            Mobile mobile = order.getMobile();
            mobile.setStatus("SOLD");
            mobileRepository.save(mobile);
        }
        if (status.equals("CANCELLED")) {
            Mobile mobile = order.getMobile();
            mobile.setStatus("AVAILABLE");
            mobileRepository.save(mobile);
        }
        Order updated = orderRepository.save(order);
        return mapToResponse(updated);
    }

    private OrderResponseDTO mapToResponse(Order order) {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setMobileId(order.getMobile().getId());
        response.setMobileBrand(order.getMobile().getBrand());
        response.setMobileModel(order.getMobile().getModel());
        response.setCustomerName(order.getCustomerName());
        response.setCustomerPhone(order.getCustomerPhone());
        response.setCustomerAddress(order.getCustomerAddress());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setOrderStatus(order.getOrderStatus());
        response.setRazorpayOrderId(order.getRazorpayOrderId());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }

}
