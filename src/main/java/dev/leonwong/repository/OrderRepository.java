package dev.leonwong.repository;

import dev.leonwong.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmail(String email);

    Optional<Order> findById(Long id);
}
