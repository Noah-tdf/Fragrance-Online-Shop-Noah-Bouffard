package org.example.termproject_bouffard.DTO;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

// Noah Bouffard 2431848

public class OrderRequestDTO {
    private String orderDate;
    private Long customerId;
    private List<OrderItemRequestDTO> items;
}
