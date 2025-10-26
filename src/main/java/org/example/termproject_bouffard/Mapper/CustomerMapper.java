package org.example.termproject_bouffard.Mapper;

import org.example.termproject_bouffard.DataAccessLayer.Customer;
import org.example.termproject_bouffard.DTO.*;
import org.springframework.stereotype.Component;

// Noah Bouffard : 2431848

@Component
public class CustomerMapper {

    public CustomerResponseDTO toResponse(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }

    public Customer fromRequestModelToEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        return customer;
    }
}
