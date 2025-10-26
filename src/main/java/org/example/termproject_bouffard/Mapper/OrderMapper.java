package org.example.termproject_bouffard.Mapper;

import org.example.termproject_bouffard.DataAccessLayer.*;
import org.example.termproject_bouffard.DTO.*;
import org.springframework.stereotype.Component;
// Noah Bouffard : 2431848

@Component
public class OrderMapper {

    public OrderResponseDTO toResponse(Order order) {
        Customer customer = order.getCustomer();
        Product product = order.getProduct();

        CustomerSummary customerSummary;
        ProductSummary productSummary;

        if (customer == null)
            customerSummary = null;
        else
            customerSummary = new CustomerSummary(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName()
            );

        if (product == null)
            productSummary = null;
        else
            productSummary = new ProductSummary(
                    product.getId(),
                    product.getName(),
                    product.getBrand()
            );

        return new OrderResponseDTO(
                order.getId(),
                customerSummary,
                productSummary,
                order.getQuantity()
        );
    }

    public Order fromRequestModelToEntity(OrderRequestDTO dto) {
        Order order = new Order();
        order.setQuantity(dto.getQuantity());
        return order;
    }
}
