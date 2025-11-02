package org.example.termproject_bouffard.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// Noah Bouffard : 2431848

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private LocalDate orderDate;
    private Long customerId;
    private Long productId;
    private int quantity;
}
