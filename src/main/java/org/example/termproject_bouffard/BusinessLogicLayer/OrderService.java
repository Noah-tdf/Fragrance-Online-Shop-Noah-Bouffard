package org.example.termproject_bouffard.BusinessLogicLayer;

import lombok.RequiredArgsConstructor;
import org.example.termproject_bouffard.DataAccessLayer.*;
import org.example.termproject_bouffard.DTO.*;
import org.example.termproject_bouffard.Mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

// Noah Bouffard : 2431848

@Service
@RequiredArgsConstructor
public class OrderService {

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

    public OrderResponseDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Order order = new Order();
        order.setOrderDate(dto.getOrderDate());
        order.setCustomer(customer);
        order.setTotalAmount(0.0);

        Order savedOrder = orderRepository.save(order);

        double total = saveOrderItems(savedOrder, dto.getItems());
        savedOrder.setTotalAmount(total);

        orderRepository.save(savedOrder);
        savedOrder.setItems(orderItemRepository.findByOrder(savedOrder));

        return orderMapper.toResponse(savedOrder);
    }

    public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");

        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        orderRepository.deleteById(id);
    }

    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        order.setCustomer(customer);
        order.setOrderDate(dto.getOrderDate());

        List<OrderItem> oldItems = orderItemRepository.findByOrder(order);
        orderItemRepository.deleteAll(oldItems); // fixed warning

        double total = saveOrderItems(order, dto.getItems());
        order.setTotalAmount(total);

        orderRepository.save(order);
        order.setItems(orderItemRepository.findByOrder(order));

        return orderMapper.toResponse(order);
    }

    private double saveOrderItems(Order order, List<OrderItemRequestDTO> itemDTOs) {
        double total = 0.0;

        for (OrderItemRequestDTO itemDTO : itemDTOs) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            double subtotal = product.getPrice() * itemDTO.getQuantity();
            OrderItem newItem = new OrderItem(order, product, itemDTO.getQuantity(), subtotal);
            orderItemRepository.save(newItem);

            total += subtotal;
        }

        return total;
    }
}
