package org.example.termproject_bouffard.DTO;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

// Noah Bouffard : 2431848

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private LocalDate orderDate;
    private Long customerId;
    private List<OrderItemRequestDTO> items;
}
