package dev.leonwong.controller;

import dev.leonwong.model.User;
import dev.leonwong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // A function to find user by jwt token
    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) {
        User user = null;
        try {
            user = userService.findUserByJwtToken(jwt);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception
        }
        return ResponseEntity.ok(user);
    }
}
