package org.example.termproject_bouffard.DTO;

import lombok.*;

// Noah Bouffard : 2431848

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    private Long productId;
    private int quantity;
}
