package org.example.termproject_bouffard.DTO;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

// Noah Bouffard : 2431848

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private LocalDate orderDate;
    private CustomerSummary customer;
    private List<OrderItemSummary> items;
    private double totalAmount;
}
