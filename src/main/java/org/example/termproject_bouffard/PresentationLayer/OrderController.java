package org.example.termproject_bouffard.PresentationLayer;

import lombok.RequiredArgsConstructor;
import org.example.termproject_bouffard.BusinessLogicLayer.OrderService;
import org.example.termproject_bouffard.DataAccessLayer.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Noah Bouffard - 2431848
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // GET all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // GET order by ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // POST a new order
    @PostMapping
    public Order addOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    // DELETE an order
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    // GET all orders for a given customer
    @GetMapping("/customer/{customerId}")
    public List<Order> getOrdersByCustomer(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }
}
