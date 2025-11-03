package org.example.termproject_bouffard;

import jakarta.transaction.Transactional;
import org.example.termproject_bouffard.DataAccessLayer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TermProjectBouffardApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TermProjectBouffardApplication.class);

    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    public TermProjectBouffardApplication(CustomerRepository customerRepo,
                                          ProductRepository productRepo,
                                          OrderRepository orderRepo,
                                          OrderItemRepository orderItemRepo) {
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(TermProjectBouffardApplication.class, args);
        logger.info("Fragrance Online Shop Backend Started Successfully!");
    }

    @Override
    @Transactional
    public void run(String... args) {
        List<Customer> customers = createCustomers();
        List<Product> products = createProducts();

        customerRepo.saveAll(customers);
        productRepo.saveAll(products);

        List<Order> orders = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();

        for (int i = 0; i < customers.size(); i++) {
            Order order = new Order();
            order.setCustomer(customers.get(i));
            order.setOrderDate(LocalDate.now().minusDays((int) (Math.random() * 30)));
            orderRepo.save(order);

            Product product = products.get(i % products.size());
            int qty = (i % 3) + 1;
            double subtotal = product.getPrice() * qty;
            OrderItem item = new OrderItem(order, product, qty, subtotal);

            orderItems.add(item);
            orders.add(order);
        }

        orderItemRepo.saveAll(orderItems);

        orders.forEach(order -> {
            double total = orderItems.stream()
                    .filter(i -> i.getOrder().equals(order))
                    .mapToDouble(OrderItem::getSubtotal)
                    .sum();
            order.setTotalAmount(total);
        });
        orderRepo.saveAll(orders);

        logger.info("Seeded {} customers, {} products, {} orders, {} order items",
                customers.size(), products.size(), orders.size(), orderItems.size());
    }

    private List<Customer> createCustomers() {
        return List.of(
                new Customer("Noah", "Bouffard", "noah@example.com", "123 Elm Street", "438-555-1212"),
                new Customer("Lucas", "Smith", "lucas@example.com", "456 Oak Avenue", "438-555-3434"),
                new Customer("Emma", "Lopez", "emma@example.com", "789 Maple Road", "438-555-4545"),
                new Customer("Olivia", "Chen", "olivia@example.com", "321 Pine Blvd", "438-555-5656"),
                new Customer("Liam", "Garcia", "liam@example.com", "999 Birch Lane", "438-555-6767"),
                new Customer("Sophia", "Martinez", "sophia@example.com", "234 Cedar Crescent", "438-555-7878"),
                new Customer("Mason", "Nguyen", "mason@example.com", "555 Birchwood Dr", "438-555-8989"),
                new Customer("Ava", "Hernandez", "ava@example.com", "876 Poplar St", "438-555-9090"),
                new Customer("Ethan", "Patel", "ethan@example.com", "300 Cherry Ave", "438-555-1010"),
                new Customer("Mia", "Rossi", "mia@example.com", "500 Maplewood Rd", "438-555-1111")
        );
    }

    private List<Product> createProducts() {
        return List.of(
                new Product("YSL Babycat", "Yves Saint Laurent", "Warm vanilla amber scent", 320.00, "Amber, Vanilla", "Unisex"),
                new Product("Parfums de Marly Althaïr", "Parfums de Marly", "Vanilla and wood blend", 220.00, "Vanilla, Cedarwood", "Male"),
                new Product("Xerjoff Erba Pura", "Xerjoff", "Fruity citrus amber blend", 250.00, "Citrus, Amber", "Unisex"),
                new Product("Louis Vuitton Pacific Chill", "Louis Vuitton", "Citrus aromatic with mint", 310.00, "Citrus, Mint", "Unisex"),
                new Product("Kajal Almaz", "Kajal", "Fruity-sweet amber", 270.00, "Amber, Peach", "Female"),
                new Product("Initio Side Effect", "Initio", "Vanilla, rum, and tobacco blend", 280.00, "Vanilla, Tobacco", "Male"),
                new Product("Creed Aventus", "Creed", "Pineapple and smoky wood blend", 400.00, "Pineapple, Birch", "Male"),
                new Product("Tom Ford Lost Cherry", "Tom Ford", "Sweet cherry and almond accord", 340.00, "Cherry, Almond", "Female"),
                new Product("Maison Francis Kurkdjian Baccarat Rouge 540", "MFK", "Amber floral masterpiece", 350.00, "Amber, Jasmine", "Unisex"),
                new Product("Versace Eros Flame", "Versace", "Fresh spicy citrus scent", 180.00, "Citrus, Vanilla", "Male")
        );
    }
}
