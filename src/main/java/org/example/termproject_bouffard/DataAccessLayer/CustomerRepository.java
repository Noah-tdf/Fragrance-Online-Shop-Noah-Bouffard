package org.example.termproject_bouffard.DataAccessLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Noah Bouffard : 2431848

public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Optional<Customer> findByEmail(String email);
}
