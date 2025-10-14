package org.example.termproject_bouffard.PresentationLayer;

import lombok.RequiredArgsConstructor;
import org.example.termproject_bouffard.BusinessLogicLayer.ProductService;
import org.example.termproject_bouffard.DataAccessLayer.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Noah Bouffard - 2431848
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // GET all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // POST a new product
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // DELETE a product
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    // GET all products for a given order
    @GetMapping("/order/{orderId}")
    public List<Product> getProductsByOrder(@PathVariable Long orderId) {
        return productService.getProductsByOrderId(orderId);
    }
}
