package org.example.termproject_bouffard.BusinessLogicLayer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.termproject_bouffard.DataAccessLayer.*;
import org.example.termproject_bouffard.DTO.*;
import org.example.termproject_bouffard.Mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
// Noah Bouffard : 2431848

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponseDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product product = productMapper.fromRequestModelToEntity(dto);
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        existing.setName(dto.getName());
        existing.setBrand(dto.getBrand());
        existing.setPrice(dto.getPrice());
        existing.setDescription(dto.getDescription());
        existing.setNotes(dto.getNotes());
        existing.setCategory(dto.getCategory());

        return productMapper.toResponse(productRepository.save(existing));
    }

    public List<ProductResponseDTO> getProductsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        List<OrderItem> items = orderItemRepository.findByOrder(order);

        return items.stream()
                .map(item -> productMapper.toResponse(item.getProduct()))
                .toList();
    }


    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found"));

        List<OrderItem> items = orderItemRepository.findByProduct(product);
        if (!items.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete product that is part of existing orders."
            );
        }

        productRepository.delete(product);
    }
}
