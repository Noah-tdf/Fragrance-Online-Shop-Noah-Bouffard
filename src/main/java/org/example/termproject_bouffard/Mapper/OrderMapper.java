package org.example.termproject_bouffard.Mapper;

import org.example.termproject_bouffard.DataAccessLayer.*;
import org.example.termproject_bouffard.DTO.*;
import org.springframework.stereotype.Component;
import java.util.List;

// Noah Bouffard : 2431848

@Component
public class OrderMapper {

    public OrderResponseDTO toResponse(Order order) {
        Customer c = order.getCustomer();

        CustomerSummary customerSummary = new CustomerSummary(
                c.getId(),
                c.getFirstName(),
                c.getLastName()
        );

        List<OrderItemSummary> itemSummaries = order.getItems()
                .stream()
                .map(item -> new OrderItemSummary(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getBrand(),
                        item.getQuantity(),
                        item.getSubtotal()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getOrderDate(),
                customerSummary,
                itemSummaries,
                order.getTotalAmount()
        );
    }
}
