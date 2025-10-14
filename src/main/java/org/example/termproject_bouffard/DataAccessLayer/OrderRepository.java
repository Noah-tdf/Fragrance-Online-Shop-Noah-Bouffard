package org.example.termproject_bouffard.DataAccessLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Noah Bouffard : 2431848
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByCustomer(Customer customer);

    
    List<Order> findByCustomerId(Long customerId);
}
