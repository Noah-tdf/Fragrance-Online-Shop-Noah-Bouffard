package org.example.termproject_bouffard.Mapper;

import org.example.termproject_bouffard.DataAccessLayer.Product;
import org.example.termproject_bouffard.DTO.ProductRequestDTO;
import org.example.termproject_bouffard.DTO.ProductResponseDTO;
import org.springframework.stereotype.Component;

// Noah Bouffard : 2431848

@Component
public class ProductMapper {

    // Convert Product entity → ProductResponseDTO
    public ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getDescription(),
                product.getNotes(),
                product.getCategory()
        );
    }

    // Convert ProductRequestDTO → Product entity
    public Product fromRequestModelToEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setNotes(dto.getNotes());
        product.setCategory(dto.getCategory());
        return product;
    }
}
