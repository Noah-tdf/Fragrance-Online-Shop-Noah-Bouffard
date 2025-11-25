package org.example.termproject_bouffard.BusinessLogicLayer;

import lombok.RequiredArgsConstructor;
import org.example.termproject_bouffard.DataAccessLayer.*;
import org.example.termproject_bouffard.DTO.*;
import org.example.termproject_bouffard.Mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    // noah bouffard : 2431848

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    public OrderResponseDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Order order = new Order();
        order.setOrderDate(LocalDate.parse(dto.getOrderDate()));
        order.setCustomer(customer);
        order.setTotalAmount(0.0);

        Order savedOrder = orderRepository.save(order);
        updateItems(savedOrder, dto.getItems());

        return orderMapper.toResponse(savedOrder);
    }

    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        order.setCustomer(customer);
        order.setOrderDate(LocalDate.parse(dto.getOrderDate()));

        updateItems(order, dto.getItems());

        return orderMapper.toResponse(order);
    }

    private void updateItems(Order order, List<OrderItemRequestDTO> items) {

        List<OrderItem> oldItems = orderItemRepository.findByOrder(order);
        Map<Long, OrderItem> existing = new HashMap<>();

        for (OrderItem oi : oldItems) {
            existing.put(oi.getProduct().getId(), oi);
        }

        double total = 0.0;

        for (OrderItemRequestDTO req : items) {
            Product product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            double subtotal = product.getPrice() * req.getQuantity();
            total += subtotal;

            OrderItem existingItem = existing.remove(req.getProductId());

            if (existingItem != null) {
                existingItem.setQuantity(req.getQuantity());
                existingItem.setSubtotal(subtotal);
                orderItemRepository.save(existingItem);
            } else {
                OrderItem newItem = new OrderItem(order, product, req.getQuantity(), subtotal);
                orderItemRepository.save(newItem);
            }
        }

        orderItemRepository.deleteAll(existing.values());

        order.setTotalAmount(total);
        orderRepository.save(order);

        List<OrderItem> updated = orderItemRepository.findByOrder(order);
        order.setItems(updated);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        orderRepository.deleteById(id);
    }
}
