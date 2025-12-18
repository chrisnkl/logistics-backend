package nikolaou.christos.backend.order_processing.service;

import lombok.AllArgsConstructor;
import nikolaou.christos.backend.order_processing.dto.OrderProcessResponse;
import nikolaou.christos.backend.order_processing.dto.OrderRequest;
import nikolaou.christos.backend.order_processing.exception.FailedCostCalculationException;
import nikolaou.christos.backend.order_processing.model.Order;
import nikolaou.christos.backend.order_processing.repository.OrderRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProcessingService orderProcessingService;

    //TODO: Implement order processing methods

    public ResponseEntity<OrderProcessResponse> createOrder(@NotNull final OrderRequest orderRequest) {
        Order order = new Order(orderRequest);
        Order saved = orderRepository.save(order);

        // Process the order asynchronously
        try {
            orderProcessingService.processOrder(saved.getId());

            return ResponseEntity
                    .accepted()
                    .body(new OrderProcessResponse(HttpStatus.ACCEPTED.value(), String.format("Order %s is being processed.", saved.getId())));

        } catch(FailedCostCalculationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OrderProcessResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OrderProcessResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred."));
        }

    }

    public ResponseEntity<String> getOrderStatus(Long orderId) {
        return ResponseEntity.ok("Order is being processed.");
    }

}
