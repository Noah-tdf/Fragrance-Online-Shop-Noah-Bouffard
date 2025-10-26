package org.example.termproject_bouffard.DTO;

import lombok.*;
// Noah Bouffard : 2431848

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private CustomerSummary customer;
    private ProductSummary product;
    private int quantity;
}
