package org.example.termproject_bouffard.Mapper;

import org.example.termproject_bouffard.DataAccessLayer.*;
import org.example.termproject_bouffard.DTO.*;
import org.springframework.stereotype.Component;

// Noah Bouffard : 2431848

@Component
public class ProductMapper {

    public ProductResponseDTO toResponse(Product product) {
        Customer owner = product.getCustomer();
        CustomerSummary ownerSummary;
        if (owner == null)
            ownerSummary = null;
        else
            ownerSummary = new CustomerSummary(owner.getId(), owner.getFirstName(), owner.getLastName());

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getDescription(),
                ownerSummary
        );
    }

    public Product fromRequestModelToEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        return product;
    }
}
