package dev.leonwong.service;

import dev.leonwong.model.Cart;
import dev.leonwong.model.CartItem;
import dev.leonwong.model.Product;
import dev.leonwong.model.User;
import dev.leonwong.repository.CartItemRepository;
import dev.leonwong.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Override
    public Cart findCartByUserEmail(String email) throws Exception {
        User user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(user);
            cart.setTotal(0L);
            return cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public Cart addItemToCart(String email, Product product, int quantity) throws Exception {
        User user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(user);
            cart.setTotal(0L);
            cartRepository.save(cart);
        }

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(0);
            cartItem.setTotalPrice(0L);
        }

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItemRepository.save(cartItem);

        updateCartTotal(cart);
        return cart;
    }

    public Cart removeItemFromCart(String email, Product product) throws Exception {
        User user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart != null) {
            CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
            if (cartItem != null) {
                cartItemRepository.delete(cartItem);
                updateCartTotal(cart);
            }
        }
        return cart;
    }

    public void updateCartTotal(Cart cart) {
        Long total = 0L;
        for (CartItem cartItem : cart.getCartItems()) {
            total += cartItem.getTotalPrice();
        }
        cart.setTotal(total);
        cartRepository.save(cart);
    }

    //@Transactional
    @Override
    public Cart clearCart(String email) throws Exception {
        User user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart != null) {
            try {
                //cartItemRepository.deleteByCart(cart);
                cart.setTotal(0L);
                cart.getCartItems().clear();
                cartRepository.save(cart);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("Cart not found");
        }
        return cart;
    }
}