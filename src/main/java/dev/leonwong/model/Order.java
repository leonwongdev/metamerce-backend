package dev.leonwong.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    private String orderStatus;

    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    //You should include cascade="all" (if using xml) or cascade=CascadeType.ALL (if using annotations) on your collection mapping.
    //
    //This happens because you have a collection in your entity, and that collection has one or more items which are not present in the database. By specifying the above options you tell hibernate to save them to the database when saving their parent.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    private int totalItem;

    private Long totalPrice;
}
