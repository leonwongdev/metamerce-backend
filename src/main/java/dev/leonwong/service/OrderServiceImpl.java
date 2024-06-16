package dev.leonwong.service;

import dev.leonwong.model.Address;
import dev.leonwong.model.Order;
import dev.leonwong.repository.AddressRepository;
import dev.leonwong.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUserEmail(String email) {
        return orderRepository.findByCustomerEmail(email);
    }

    // Save address
    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }


    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    // Update order status
    @Override
    public Order updateOrderStatus(Long id, String status) throws Exception {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new Exception("Order not found");
        }
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
}
