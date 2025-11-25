package org.example.termproject_bouffard.DTO;

import lombok.*;
import java.util.List;

// Noah Bouffard 2431848

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private String orderDate;
    private Long customerId;
    private List<OrderItemRequestDTO> items;
}
