package org.example.termproject_bouffard;

import jakarta.transaction.Transactional;
import org.example.termproject_bouffard.DataAccessLayer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//

@SpringBootApplication
public class TermProjectBouffardApplication {

    private static final Logger logger = LoggerFactory.getLogger(TermProjectBouffardApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TermProjectBouffardApplication.class, args);
        logger.info("Fragrance Online Shop Backend Started Successfully!");
    }

    @Bean
    @Transactional
    public CommandLineRunner seedData(
            CustomerRepository customerRepo,
            ProductRepository productRepo,
            OrderRepository orderRepo,
            OrderItemRepository orderItemRepo
    ) {
        return args -> {

            if (customerRepo.count() > 0 || productRepo.count() > 0) {
                logger.warn("Seed skipped — database already contains data.");
                return;
            }

            logger.info("Seeding 30 customers, products, and orders...");

            List<Customer> customers = create30Customers();
            customerRepo.saveAll(customers);

            List<Product> products = create30Products();
            productRepo.saveAll(products);

            List<Order> orders = new ArrayList<>();
            List<OrderItem> orderItems = new ArrayList<>();
            Random random = new Random();

            for (int i = 0; i < 30; i++) {
                Customer customer = customers.get(i);

                Order order = new Order();
                order.setCustomer(customer);
                order.setOrderDate(LocalDate.now().minusDays(random.nextInt(30)));
                order.setTotalAmount(0.0);

                orderRepo.save(order);

                int itemCount = 1 + random.nextInt(3);

                for (int x = 0; x < itemCount; x++) {
                    Product p = products.get(random.nextInt(products.size()));
                    int qty = 1 + random.nextInt(3);

                    double subtotal = p.getPrice() * qty;

                    OrderItem item = new OrderItem(order, p, qty, subtotal);
                    orderItems.add(item);
                }

                orders.add(order);
            }

            orderItemRepo.saveAll(orderItems);

            for (Order order : orders) {
                double total = orderItemRepo.findByOrder(order)
                        .stream()
                        .mapToDouble(OrderItem::getSubtotal)
                        .sum();
                order.setTotalAmount(total);
            }

            orderRepo.saveAll(orders);

            logger.info("Seeding complete.");
        };
    }

    private List<Customer> create30Customers() {
        return List.of(
                new Customer("Noah", "Bouffard", "noah@example.com", "123 Elm Street", "438-555-1111"),
                new Customer("Lucas", "Smith", "lucas@example.com", "456 Oak Avenue", "438-555-1112"),
                new Customer("Emma", "Lopez", "emma@example.com", "789 Maple Road", "438-555-1113"),
                new Customer("Olivia", "Chen", "olivia@example.com", "321 Pine Blvd", "438-555-1114"),
                new Customer("Liam", "Garcia", "liam@example.com", "999 Birch Lane", "438-555-1115"),
                new Customer("Sophia", "Martinez", "sophia@example.com", "234 Cedar Crescent", "438-555-1116"),
                new Customer("Michael", "Johnson", "michael@example.com", "600 River Rd", "438-555-1117"),
                new Customer("Ava", "Hernandez", "ava@example.com", "876 Poplar St", "438-555-1118"),
                new Customer("Ethan", "Patel", "ethan@example.com", "300 Cherry Ave", "438-555-1119"),
                new Customer("Mia", "Rossi", "mia@example.com", "500 Maplewood Rd", "438-555-1120"),

                new Customer("Isabella", "Wong", "bella@example.com", "120 Willow St", "438-555-1121"),
                new Customer("James", "Davis", "james@example.com", "22 Hamilton Rd", "438-555-1122"),
                new Customer("Benjamin", "Clark", "ben@example.com", "42 Chestnut Dr", "438-555-1123"),
                new Customer("Charlotte", "Kim", "charlotte@example.com", "81 Sunset Blvd", "438-555-1124"),
                new Customer("Henry", "Moore", "henry@example.com", "19 Birchwood Pl", "438-555-1125"),
                new Customer("Amelia", "Taylor", "amelia@example.com", "72 Lavender Ln", "438-555-1126"),
                new Customer("Aiden", "Brown", "aiden@example.com", "63 Lakeshore Dr", "438-555-1127"),
                new Customer("Grace", "White", "grace@example.com", "15 Cypress Ave", "438-555-1128"),
                new Customer("Jacob", "Baker", "jacob@example.com", "94 Hillcrest Rd", "438-555-1129"),
                new Customer("Ella", "Gray", "ella@example.com", "18 Meadow Ln", "438-555-1130"),

                new Customer("Sebastian", "Rivera", "seb@example.com", "7 Oak Park", "438-555-1131"),
                new Customer("Harper", "Mitchell", "harper@example.com", "5 Rosewood Ct", "438-555-1132"),
                new Customer("Alexander", "Hughes", "alex@example.com", "670 Aspen Way", "438-555-1133"),
                new Customer("Zoe", "King", "zoe@example.com", "455 Evergreen St", "438-555-1134"),
                new Customer("Matthew", "Bailey", "matt@example.com", "220 Orchard Ln", "438-555-1135"),
                new Customer("Scarlett", "Adams", "scarlett@example.com", "56 Brookside Dr", "438-555-1136"),
                new Customer("Joshua", "Ward", "josh@example.com", "11 Pine Hollow", "438-555-1137"),
                new Customer("Chloe", "Parker", "chloe@example.com", "99 Forest Hill", "438-555-1138"),
                new Customer("Daniel", "Evans", "dan@example.com", "740 Maple Grove", "438-555-1139"),
                new Customer("Layla", "Morgan", "layla@example.com", "150 Cedar Grove", "438-555-1140")
        );
    }

    private List<Product> create30Products() {
        return List.of(
                new Product("YSL Babycat", "YSL", "Warm vanilla amber scent", 320.00, "Amber, Vanilla", "Unisex"),
                new Product("Althaïr", "Parfums de Marly", "Vanilla and wood blend", 220.00, "Vanilla, Cedarwood", "Male"),
                new Product("Erba Pura", "Xerjoff", "Fruity citrus amber blend", 250.00, "Citrus, Amber", "Unisex"),
                new Product("Pacific Chill", "Louis Vuitton", "Fresh citrus aromatic", 310.00, "Citrus, Mint", "Unisex"),
                new Product("Almaz", "Kajal", "Fruity-sweet amber", 270.00, "Amber, Peach", "Female"),
                new Product("Side Effect", "Initio", "Rum, tobacco, vanilla", 280.00, "Vanilla, Tobacco", "Male"),
                new Product("Aventus", "Creed", "Pineapple-smoke blend", 400.00, "Pineapple, Birch", "Male"),
                new Product("Lost Cherry", "Tom Ford", "Sweet cherry and almond", 340.00, "Cherry, Almond", "Female"),
                new Product("BR540", "MFK", "Amber floral", 350.00, "Amber, Jasmine", "Unisex"),
                new Product("Eros Flame", "Versace", "Spicy citrus", 180.00, "Citrus, Vanilla", "Male"),

                new Product("Sauvage Elixir", "Dior", "Spicy aromatic", 240.00, "Lavender, Spices", "Male"),
                new Product("Not a Perfume", "JHAG", "Clean musky amber", 150.00, "Cetalox", "Unisex"),
                new Product("Gypsy Water", "Byredo", "Soft woody aromatic", 260.00, "Vanilla, Pine", "Unisex"),
                new Product("Cloud", "Ariana Grande", "Sweet gourmand", 60.00, "Coconut, Vanilla", "Female"),
                new Product("Cedrat Boise", "Mancera", "Fresh citrus wood", 165.00, "Citrus, Cedar", "Unisex"),
                new Product("CDNIM", "Armaf", "Smoky citrus", 55.00, "Citrus, Smoke", "Male"),
                new Product("Paradoxe", "Prada", "Floral amber", 180.00, "Neroli, Amber", "Female"),
                new Product("Jazz Club", "Replica", "Rum, tobacco, vanilla", 160.00, "Rum, Vanilla", "Male"),
                new Product("Explorer", "Montblanc", "Fresh woody aromatic", 120.00, "Bergamot, Vetiver", "Male"),
                new Product("Born in Roma", "Valentino", "Sweet floral vanilla", 145.00, "Jasmine, Vanilla", "Female"),

                new Product("Aventus for Her", "Creed", "Fruity floral", 420.00, "Apple, Lemon", "Female"),
                new Product("Oud for Greatness", "Initio", "Smooth oud blend", 350.00, "Oud, Lavender", "Unisex"),
                new Product("Code Parfum", "Armani", "Sweet woody floral", 180.00, "Tonka, Bergamot", "Male"),
                new Product("Libre", "YSL", "Floral lavender vanilla", 155.00, "Lavender, Vanilla", "Female"),
                new Product("Bleu de Chanel", "Chanel", "Clean woody aromatic", 180.00, "Citrus, Sandalwood", "Male"),
                new Product("Wood Sage & Sea Salt", "Jo Malone", "Fresh airy wood", 150.00, "Sea Salt, Sage", "Unisex"),
                new Product("Reflection Man", "Amouage", "White floral woody", 290.00, "Neroli, Jasmine", "Male"),
                new Product("Gentle Fluidity Gold", "MFK", "Sweet vanilla amber", 250.00, "Vanilla, Amber", "Unisex"),
                new Product("Angel Share", "Killian", "Cinnamon boozy gourmand", 210.00, "Cinnamon, Vanilla", "Unisex"),
                new Product("Delina", "PdM", "Rose fruity floral", 335.00, "Rose, Lychee", "Female")
        );
    }
}
