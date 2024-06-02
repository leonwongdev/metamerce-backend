package dev.leonwong.repository;

import dev.leonwong.model.Cart;
import dev.leonwong.model.CartItem;
import dev.leonwong.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    public CartItem findByCartAndProduct(Cart cart, Product product);
    public void deleteByCart(Cart cart);
}
