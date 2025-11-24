package org.example.termproject_bouffard.Mapper;

import lombok.RequiredArgsConstructor;
import org.example.termproject_bouffard.DataAccessLayer.*;
import org.example.termproject_bouffard.DTO.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final CustomerMapper customerMapper;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public OrderResponseDTO toResponse(Order order) {
        if (order == null) return null;

        return new OrderResponseDTO(
                order.getId(),
                order.getOrderDate().format(DATE_FORMAT),
                order.getTotalAmount(),
                customerMapper.toResponse(order.getCustomer()),
                order.getItems()
                        .stream()
                        .map(this::toItemResponse)
                        .toList()
        );
    }

    public OrderItemResponseDTO toItemResponse(OrderItem item) {
        return new OrderItemResponseDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}
