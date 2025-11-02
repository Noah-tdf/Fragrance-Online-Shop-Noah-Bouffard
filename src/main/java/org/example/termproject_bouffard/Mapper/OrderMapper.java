package org.example.termproject_bouffard.Mapper;

import org.example.termproject_bouffard.DataAccessLayer.*;
import org.example.termproject_bouffard.DTO.*;
import org.springframework.stereotype.Component;

// Noah Bouffard : 2431848

@Component
public class OrderMapper {

    public OrderResponseDTO toResponse(Order order) {
        Customer c = order.getCustomer();
        Product p = order.getProduct();

        CustomerSummary customerSummary = new CustomerSummary(
                c.getId(), c.getFirstName(), c.getLastName()
        );

        ProductSummary productSummary = new ProductSummary(
                p.getId(), p.getName(), p.getBrand()
        );

        return new OrderResponseDTO(
                order.getId(),
                order.getOrderDate(),
                customerSummary,
                productSummary,
                order.getQuantity(),
                order.getTotalAmount()
        );
    }

    public Order fromRequestModelToEntity(OrderRequestDTO dto) {
        Order order = new Order();
        order.setOrderDate(dto.getOrderDate());
        order.setQuantity(dto.getQuantity());
        return order;
    }
}
