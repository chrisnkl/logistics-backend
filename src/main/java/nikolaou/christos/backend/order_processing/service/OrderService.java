package nikolaou.christos.backend.order_processing.service;

import lombok.AllArgsConstructor;
import nikolaou.christos.backend.order_processing.dto.OrderRequest;
import nikolaou.christos.backend.order_processing.dto.OrderResponse;
import nikolaou.christos.backend.order_processing.exception.OrderNotExistsException;
import nikolaou.christos.backend.order_processing.model.Order;
import nikolaou.christos.backend.order_processing.repository.OrderRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProcessingService orderProcessingService;

    //TODO: Implement order processing methods

    public ResponseEntity<Long> createOrder(@NotNull final OrderRequest orderRequest) {
        Order order = new Order(orderRequest);
        Order saved = orderRepository.save(order);

        // Process the order asynchronously
        orderProcessingService.processOrder(saved.getId());

        return ResponseEntity
                .accepted()
                .body(saved.getId());
    }

    public ResponseEntity<String> getOrderStatus(Long orderId) {
        return ResponseEntity.ok("Order is being processed.");
    }

}
