package nikolaou.christos.backend.order_processing;

import com.nimbusds.jose.shaded.gson.Gson;
import lombok.AllArgsConstructor;
import nikolaou.christos.backend.order_processing.dto.OrderRequest;
import nikolaou.christos.backend.order_processing.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> processOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    /** Get the status of an order
     * @param orderId the ID of the order
     * @return the status of the order
     */
    @GetMapping("/status/{orderId}")
    public String getOrderStatus(@PathVariable(required = true) Long orderId) {
        return "Your order #" + orderId + " is being processed.";
    }

}
