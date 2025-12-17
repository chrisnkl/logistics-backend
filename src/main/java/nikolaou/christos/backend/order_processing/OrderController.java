package nikolaou.christos.backend.order_processing;

import lombok.AllArgsConstructor;
import nikolaou.christos.backend.order_processing.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /** Get the status of an order
     * @param orderId the ID of the order
     * @return the status of the order
     */
    @GetMapping("/status/{orderId}")
    public String getOrderStatus(@PathVariable Long orderId) {
        return "Your order #" + orderId + " is being processed.";
    }

}
