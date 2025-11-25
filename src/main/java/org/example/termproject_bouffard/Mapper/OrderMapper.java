package org.example.termproject_bouffard.Mapper;

import org.example.termproject_bouffard.DataAccessLayer.Order;
import org.example.termproject_bouffard.DataAccessLayer.OrderItem;
import org.example.termproject_bouffard.DTO.OrderItemResponseDTO;
import org.example.termproject_bouffard.DTO.OrderResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponseDTO toResponse(Order order) {

        List<OrderItemResponseDTO> items = order.getItems()
                .stream()
                .map(this::toItemResponse)
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getOrderDate().toString(),
                order.getTotalAmount(),
                order.getCustomer().getId(),
                order.getCustomer().getFirstName(),
                order.getCustomer().getLastName(),
                items
        );
    }

    private OrderItemResponseDTO toItemResponse(OrderItem item) {
        return new OrderItemResponseDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}
