package nikolaou.christos.backend.order_processing;

import lib.response.BackendResponse;
import lombok.AllArgsConstructor;
import nikolaou.christos.backend.order_processing.dto.OrderRequest;
import nikolaou.christos.backend.order_processing.dto.OrderResponse;
import nikolaou.christos.backend.order_processing.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BackendResponse> processOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse response = orderService.createOrder(orderRequest);
        return ResponseEntity.accepted().body(new BackendResponse(
                HttpStatus.ACCEPTED.value(),
                "Your order is currently being processed.",
                response
        ));
    }

    /** Get the status of an order
     * @param orderId the ID of the order
     * @return the status of the order
     */
    @GetMapping("/status/{orderId}")
    public ResponseEntity<BackendResponse> getOrderStatus(@PathVariable(required = true) Long orderId) {
        OrderResponse response = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(new BackendResponse(
                HttpStatus.OK.value(),
                "Order Status Retrieved",
                response.status()
        ));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<BackendResponse> getOrderDetails(@PathVariable Long orderId) {
        OrderResponse response = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(new BackendResponse(
                HttpStatus.OK.value(),
                "Order Details Retrieved",
                response
        ));
    }

}
