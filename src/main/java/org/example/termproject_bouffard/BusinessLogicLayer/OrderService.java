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

        double total = 0.0;

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            double subtotal = product.getPrice() * itemDTO.getQuantity();

            OrderItem item = new OrderItem(savedOrder, product, itemDTO.getQuantity(), subtotal);
            orderItemRepository.save(item);

            total += subtotal;
        }

        savedOrder.setTotalAmount(total);
        orderRepository.save(savedOrder);
        savedOrder.setItems(orderItemRepository.findByOrder(savedOrder));

        return orderMapper.toResponse(savedOrder);
    }

    public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");

        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream()
                .map(orderMapper::toResponse)
                .toList();
    }


    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        orderRepository.deleteById(id);
    }
}
