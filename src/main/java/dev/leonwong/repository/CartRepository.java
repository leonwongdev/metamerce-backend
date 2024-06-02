package dev.leonwong.repository;

import dev.leonwong.model.Cart;
import dev.leonwong.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    public Cart findByCustomerId(Long id);

    public void deleteByCustomerId(Long id);

}
