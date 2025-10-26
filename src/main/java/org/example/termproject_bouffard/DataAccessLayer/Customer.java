package org.example.termproject_bouffard.DataAccessLayer;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
// Noah Bouffard : 2431848

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Product> products;

    public Customer(String firstName, String lastName, String email, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
}
