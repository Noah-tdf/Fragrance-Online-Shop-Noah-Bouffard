package org.example.termproject_bouffard.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private double subtotal;
}
