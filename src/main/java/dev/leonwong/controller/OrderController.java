package dev.leonwong.controller;

import dev.leonwong.model.*;
import dev.leonwong.service.CartService;
import dev.leonwong.service.OrderService;
import dev.leonwong.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(@RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<Order> orders = orderService.getOrdersByUserEmail(user.getEmail());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Order order = orderService.findById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @Transactional
    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestHeader("Authorization") String jwt, @RequestBody Address address) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Cart cart = cartService.findCartByUserEmail(user.getEmail());
            if (cart.getCartItems().isEmpty()) {
                System.out.println("OrderController.placeOrder: Cart is empty");
                return ResponseEntity.badRequest().body(null);
            }

            Address savedAddress = orderService.saveAddress(address);

            Order order = new Order();
            order.setCustomer(cart.getCustomer());
            order.setCreatedAt(new Date());
            order.setAddress(savedAddress);
            order.setOrderStatus("PLACED");
            order.setItems(cart.getCartItems().stream().map(cartItem -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setTotalPrice(cartItem.getTotalPrice());
                return orderItem;
            }).collect(Collectors.toList()));
            order.setTotalItem(cart.getCartItems().size());
            order.setTotalPrice(cart.getTotal());

            Order savedOrder = orderService.saveOrder(order);

            // Clear the cart after placing the order
            cartService.clearCart(user.getEmail());

            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
