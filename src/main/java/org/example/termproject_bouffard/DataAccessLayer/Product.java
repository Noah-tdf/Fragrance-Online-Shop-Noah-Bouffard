package org.example.termproject_bouffard.DataAccessLayer;

import jakarta.persistence.*;
import lombok.*;
// Noah Bouffard : 2431848

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private String description;
    private double price;
    private String notes;
    private String category;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Product(String name, String brand, String description, double price,
                   String notes, String category, Order order) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.notes = notes;
        this.category = category;
        this.order = order;
    }
}
