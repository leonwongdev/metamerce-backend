package dev.leonwong.controller;

import dev.leonwong.model.Cart;
import dev.leonwong.model.Product;
import dev.leonwong.model.User;
import dev.leonwong.service.CartService;
import dev.leonwong.service.ProductService;
import dev.leonwong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Cart cart = cartService.findCartByUserEmail(user.getEmail());
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(
            @RequestHeader("Authorization") String jwt,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Product product = productService.findProductById(productId);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            Cart updatedCart = cartService.addItemToCart(user.getEmail(), product, quantity);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeItemFromCart(
            @RequestHeader("Authorization") String jwt,
            @RequestParam Long productId
    ) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Product product = productService.findProductById(productId);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            Cart updatedCart = cartService.removeItemFromCart(user.getEmail(), product);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Cart clearedCart = cartService.clearCart(user.getEmail());
            return ResponseEntity.ok(clearedCart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}