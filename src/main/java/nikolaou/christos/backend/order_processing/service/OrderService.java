package nikolaou.christos.backend.order_processing.service;

import lombok.AllArgsConstructor;
import nikolaou.christos.backend.order_processing.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    //TODO: Implement order processing methods
}
