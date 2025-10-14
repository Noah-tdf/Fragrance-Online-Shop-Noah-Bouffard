package org.example.termproject_bouffard.DataAccessLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Noah Bouffard : 2431848
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find all products belonging to a specific order
    List<Product> findByOrder(Order order);

    // Or find by order ID directly
    List<Product> findByOrderId(Long orderId);
}
