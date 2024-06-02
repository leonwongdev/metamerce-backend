package dev.leonwong.repository;


import dev.leonwong.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

        // Function to get food by id
        public Optional<Product> findById(Long id);

        // Function to get all food
        public List<Product> findAll();

}
