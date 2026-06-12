package com.mobileshop.repository;

import com.mobileshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatusOrderByCreatedAtDesc(String orderStatus);

    List<Order> findByPaymentMethodOrderByCreatedAtDesc(String paymentMethod);

    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findByMobileId(Long mobileId);

}
