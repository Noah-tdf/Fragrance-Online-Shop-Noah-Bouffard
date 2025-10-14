package org.example.termproject_bouffard.BusinessLogicLayer;

import lombok.RequiredArgsConstructor;
import org.example.termproject_bouffard.DataAccessLayer.Product;
import org.example.termproject_bouffard.DataAccessLayer.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByOrderId(Long orderId) {
        return productRepository.findByOrderId(orderId);
    }
}
