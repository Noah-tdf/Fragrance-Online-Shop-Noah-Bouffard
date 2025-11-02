package org.example.termproject_bouffard;

import org.example.termproject_bouffard.DataAccessLayer.Customer;
import org.example.termproject_bouffard.DataAccessLayer.Product;
import org.example.termproject_bouffard.DataAccessLayer.Order;
import org.example.termproject_bouffard.DataAccessLayer.CustomerRepository;
import org.example.termproject_bouffard.DataAccessLayer.ProductRepository;
import org.example.termproject_bouffard.DataAccessLayer.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Arrays;

// Noah Bouffard - 2431848
// Fragrance Online Shop Backend

@SpringBootApplication
public class TermProjectBouffardApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TermProjectBouffardApplication.class);

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public TermProjectBouffardApplication(CustomerRepository customerRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TermProjectBouffardApplication.class, args);
        logger.info("Fragrance Online Shop Backend Started Successfully!");
    }

    @Override
    public void run(String... args)  {

        Customer c1 = new Customer("Noah", "Bouffard", "noah@example.com", "123 Elm Street", "438-555-1212");
        Customer c2 = new Customer("Lucas", "Smith", "lucas@example.com", "456 Oak Avenue", "438-555-3434");
        customerRepository.saveAll(Arrays.asList(c1, c2));

        Product p1 = new Product("YSL Babycat", "Yves Saint Laurent", "Warm vanilla amber scent", 320.00, "Amber, Vanilla", "Unisex");
        Product p2 = new Product("Parfums de Marly Althaïr", "Parfums de Marly", "Vanilla and wood blend", 220.00, "Vanilla, Cedarwood", "Male");
        Product p3 = new Product("Xerjoff Erba Pura", "Xerjoff", "Fruity citrus amber blend", 250.00, "Citrus, Amber", "Unisex");
        productRepository.saveAll(Arrays.asList(p1, p2, p3));

        Order o1 = new Order(LocalDate.now(), 540.00, c1);
        o1.setProduct(p1);
        o1.setQuantity(2);

        Order o2 = new Order(LocalDate.now(), 320.00, c2);
        o2.setProduct(p2);
        o2.setQuantity(1);

        orderRepository.saveAll(Arrays.asList(o1, o2));

        for (Product product : productRepository.findAll()) {
            logger.info("Product: {}, Brand: {}, Price: ${}",
                    product.getName(), product.getBrand(), product.getPrice());
        }

        for (Customer customer : customerRepository.findAll()) {
            logger.info("Customer: {} {}, Email: {}",
                    customer.getFirstName(), customer.getLastName(), customer.getEmail());
        }

        for (Order order : orderRepository.findAll()) {
            logger.info("Order ID: {}, Product: {}, Customer: {}",
                    order.getId(),
                    order.getProduct() != null ? order.getProduct().getName() : "None",
                    order.getCustomer().getFirstName());
        }

        logger.info("Sample fragrance data successfully saved to H2 database!");
    }
}
