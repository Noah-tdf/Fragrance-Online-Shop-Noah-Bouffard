package org.example.termproject_bouffard.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    private Long productId;
    private int quantity;
}
