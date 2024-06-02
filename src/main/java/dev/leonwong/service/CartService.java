package dev.leonwong.service;

import dev.leonwong.model.Cart;
import dev.leonwong.model.Product;
import dev.leonwong.model.User;

public interface CartService {

    public Cart findCartByUserEmail(String email) throws Exception;

    public Cart addItemToCart(String email, Product product, int quantity) throws Exception;

    public Cart removeItemFromCart(String email, Product product) throws Exception;

    public Cart clearCart(String email) throws Exception;
}
