package org.example.termproject_bouffard;

import jakarta.transaction.Transactional;
import org.example.termproject_bouffard.DataAccessLayer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

// Noah Bouffard - 2431848

@SpringBootApplication
public class TermProjectBouffardApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TermProjectBouffardApplication.class);

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public TermProjectBouffardApplication(CustomerRepository customerRepository,
                                          ProductRepository productRepository,
                                          OrderRepository orderRepository,
                                          OrderItemRepository orderItemRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TermProjectBouffardApplication.class, args);
        logger.info("Fragrance Online Shop Backend Started Successfully!");
    }

    @Override
    @Transactional
    public void run(String... args) {

        Customer c1 = new Customer("Noah", "Bouffard", "noah@example.com", "123 Elm Street", "438-555-1212");
        Customer c2 = new Customer("Lucas", "Smith", "lucas@example.com", "456 Oak Avenue", "438-555-3434");
        customerRepository.saveAll(Arrays.asList(c1, c2));

        Product p1 = new Product("YSL Babycat", "Yves Saint Laurent", "Warm vanilla amber scent", 320.00, "Amber, Vanilla", "Unisex");
        Product p2 = new Product("Parfums de Marly Althaïr", "Parfums de Marly", "Vanilla and wood blend", 220.00, "Vanilla, Cedarwood", "Male");
        Product p3 = new Product("Xerjoff Erba Pura", "Xerjoff", "Fruity citrus amber blend", 250.00, "Citrus, Amber", "Unisex");
        productRepository.saveAll(Arrays.asList(p1, p2, p3));

        Order o1 = new Order();
        o1.setOrderDate(LocalDate.now());
        o1.setCustomer(c1);

        Order o2 = new Order();
        o2.setOrderDate(LocalDate.now());
        o2.setCustomer(c2);

        orderRepository.saveAll(Arrays.asList(o1, o2));

        OrderItem i1 = new OrderItem(o1, p1, 2, p1.getPrice() * 2);
        OrderItem i2 = new OrderItem(o1, p3, 1, p3.getPrice());
        OrderItem i3 = new OrderItem(o2, p2, 1, p2.getPrice());
        orderItemRepository.saveAll(List.of(i1, i2, i3));

        o1.setTotalAmount(i1.getSubtotal() + i2.getSubtotal());
        o2.setTotalAmount(i3.getSubtotal());
        orderRepository.saveAll(Arrays.asList(o1, o2));

        productRepository.findAll().forEach(product ->
                logger.info("Product: {}, Brand: {}, Price: ${}", product.getName(), product.getBrand(), product.getPrice()));

        customerRepository.findAll().forEach(customer ->
                logger.info("Customer: {} {}, Email: {}", customer.getFirstName(), customer.getLastName(), customer.getEmail()));

        orderRepository.findAll().forEach(order -> {
            logger.info("Order ID: {}, Customer: {}, Total: ${}", order.getId(), order.getCustomer().getFirstName(), order.getTotalAmount());
            order.getItems().forEach(item ->
                    logger.info("   -> Product: {}, Qty: {}, Subtotal: ${}", item.getProduct().getName(), item.getQuantity(), item.getSubtotal()));
        });

        logger.info("Sample fragrance data successfully saved to H2 database!");
    }
}
