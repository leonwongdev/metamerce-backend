package dev.leonwong.service;

import dev.leonwong.model.Address;
import dev.leonwong.model.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);

    List<Order> getOrdersByUserEmail(String email);

    Order findById(Long id);

    List<Order> findAll();

    // Save address
    Address saveAddress(Address address);

    // Update order status
    Order updateOrderStatus(Long id, String status) throws Exception;
}
