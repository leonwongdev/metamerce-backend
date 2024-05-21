package dev.leonwong.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.leonwong.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO) // Generate id automatically
    @Id
    private Long id;

    private String fullname;

    private String email;

    private String password;

    private UserRole role;

    @JsonIgnore // When I fetch a user, I do not need a list of order, this will be done by a separate API
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // When user is deleted, delete all address
    private List<Address> addresses = new ArrayList<>();
}
