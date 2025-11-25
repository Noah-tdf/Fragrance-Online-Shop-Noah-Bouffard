package org.example.termproject_bouffard.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String orderDate;
    private double totalAmount;

    private Long customerId;
    private String customerFirstName;
    private String customerLastName;

    private List<OrderItemResponseDTO> items;
}
