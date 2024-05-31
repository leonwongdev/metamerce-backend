package dev.leonwong.controller;

import dev.leonwong.config.JwtProvider;
import dev.leonwong.model.User;
import dev.leonwong.repository.CartRepository;
import dev.leonwong.repository.UserRepository;
import dev.leonwong.response.AuthResponse;
import dev.leonwong.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    // Cart repository
    @Autowired
    private CartRepository cartRepository;

    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        User dbUser = userRepository.findByEmail(user.getEmail());
        if (dbUser != null) {
            throw new Exception("User with this email already exists");
        }

        // if the user is not present in the database, then we will create a new user
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFullname(user.getFullname());
        newUser.setRole(user.getRole());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(newUser);
        return null;
    }
}
