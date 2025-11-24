package org.example.termproject_bouffard.PresentationLayer;

import lombok.RequiredArgsConstructor;
import org.example.termproject_bouffard.BusinessLogicLayer.OrderService;
import org.example.termproject_bouffard.DTO.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
// Noah Bouffard : 2431848

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

        private final OrderService orderService;


        @GetMapping
        public List<OrderResponseDTO> getAllOrders() {
            return orderService.getAllOrders();
        }

        @GetMapping("/{id}")
        public OrderResponseDTO getOrderById(@PathVariable Long id) {
            return orderService.getOrderById(id);
        }

        @PostMapping
        public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO dto) {
            OrderResponseDTO created = orderService.createOrder(dto);
            return ResponseEntity
                    .created(URI.create("/orders/" + created.getId()))
                    .body(created);
        }

        @GetMapping("/customers/{customerId}")
        public List<OrderResponseDTO> getOrdersByCustomerId(@PathVariable Long customerId) {
            return orderService.getOrdersByCustomerId(customerId);
        }

       @DeleteMapping("/{id}")
          public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        }

    @PutMapping("/{id}")
    public OrderResponseDTO updateOrder(
            @PathVariable Long id,
            @RequestBody OrderRequestDTO dto
    ) {
        return orderService.updateOrder(id, dto);
    }





}
