package org.example.termproject_bouffard.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

// Noah Bouffard : 2431848

@Data
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private LocalDate orderDate;
    private CustomerSummary customer;
    private ProductSummary product;
    private int quantity;
    private double totalAmount;
}
