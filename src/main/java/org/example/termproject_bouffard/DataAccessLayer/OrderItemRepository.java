package org.example.termproject_bouffard.DataAccessLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Noah Bouffard : 2431848
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByProduct(Product product);

    List<OrderItem> findByOrder(Order order);
}
