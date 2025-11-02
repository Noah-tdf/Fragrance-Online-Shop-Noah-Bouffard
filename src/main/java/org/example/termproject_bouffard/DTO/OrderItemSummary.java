package org.example.termproject_bouffard.DTO;

import lombok.*;

// Noah Bouffard : 2431848

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemSummary {
    private Long productId;
    private String productName;
    private String brand;
    private int quantity;
    private double subtotal;
}
