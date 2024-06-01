package dev.leonwong.controller;

import dev.leonwong.config.JwtProvider;
import dev.leonwong.model.Cart;
import dev.leonwong.model.User;
import dev.leonwong.model.UserRole;
import dev.leonwong.repository.CartRepository;
import dev.leonwong.repository.UserRepository;
import dev.leonwong.request.LoginRequest;
import dev.leonwong.response.AuthResponse;
import dev.leonwong.service.CustomerUserDetailsService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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

    @PostMapping("/signup")
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

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register Success");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) throws BadRequestException {

        String username = loginRequest.getEmail();

        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register Success");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        authResponse.setRole(UserRole.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) throws BadRequestException {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadRequestException("Invalid username, username not found.");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }


}
