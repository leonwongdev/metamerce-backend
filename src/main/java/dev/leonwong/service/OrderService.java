package dev.leonwong.service;

import dev.leonwong.model.Address;
import dev.leonwong.model.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    List<Order> getOrdersByUserEmail(String email);

    Order findById(Long id);

    // Save address
    Address saveAddress(Address address);
}
