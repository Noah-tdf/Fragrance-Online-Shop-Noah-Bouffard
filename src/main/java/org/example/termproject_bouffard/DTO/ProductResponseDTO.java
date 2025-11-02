package org.example.termproject_bouffard.DTO;

import lombok.*;
// Noah Bouffard : 2431848

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String brand;
    private double price;
    private String description;
    private String notes;
    private String category;
}
