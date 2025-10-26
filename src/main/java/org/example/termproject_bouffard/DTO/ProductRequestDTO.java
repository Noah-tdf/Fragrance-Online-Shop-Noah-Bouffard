package org.example.termproject_bouffard.DTO;

import lombok.*;
// Noah Bouffard : 2431848

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private String name;
    private String brand;
    private double price;
    private String description;
    private Long customerId;
}
