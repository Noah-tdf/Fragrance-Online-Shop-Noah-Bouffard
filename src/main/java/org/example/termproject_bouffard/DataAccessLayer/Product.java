package org.example.termproject_bouffard.DataAccessLayer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    // Noah Bouffard
    // 2431848

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private String description;
    private double price;
    private String fragranceNotes;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Product(String name, String brand, String description,
                   double price, String fragranceNotes, String gender, Order order) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.fragranceNotes = fragranceNotes;
        this.gender = gender;
        this.order = order;
    }
}
