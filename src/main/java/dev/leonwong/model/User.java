package dev.leonwong.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    // Hide this field in response but allow it in request, using JsonIgnore will hide it in both request and response
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private UserRole role = UserRole.ROLE_CUSTOMER;

    @JsonIgnore // When I fetch a user, I do not need a list of order, this will be done by a separate API
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
}
