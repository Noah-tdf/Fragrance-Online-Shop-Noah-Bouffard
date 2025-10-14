package org.example.termproject_bouffard.PresentationLayer;

import lombok.RequiredArgsConstructor;
import org.example.termproject_bouffard.BusinessLogicLayer.CustomerService;
import org.example.termproject_bouffard.DataAccessLayer.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Noah Bouffard - 2431848
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }


    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }


    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
