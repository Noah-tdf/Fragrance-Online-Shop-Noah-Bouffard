package org.example.termproject_bouffard.DTO;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String orderDate;
    private double totalAmount;
    private CustomerResponseDTO customer;
    private List<OrderItemResponseDTO> items;
}
