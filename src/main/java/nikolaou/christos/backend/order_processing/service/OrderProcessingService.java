package nikolaou.christos.backend.order_processing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nikolaou.christos.backend.order_processing.dto.OrderResponse;
import nikolaou.christos.backend.order_processing.exception.FailedCostCalculationException;
import nikolaou.christos.backend.order_processing.exception.OrderNotExistsException;
import nikolaou.christos.backend.order_processing.model.Order;
import nikolaou.christos.backend.order_processing.repository.OrderRepository;
import nikolaou.christos.backend.order_processing.strategy.ShippingStrategy;
import nikolaou.christos.backend.order_processing.strategy.ShippingStrategyContext;
import nikolaou.christos.backend.order_processing.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Lazy
@RequiredArgsConstructor
@Slf4j
public class OrderProcessingService {

    @Value("${async.processing.delay:3000}")
    private long asyncProcessDelay;

    private final ShippingStrategyContext shippingStrategyContext;
    private final OrderRepository orderRepository;

    @Async
    public void processOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotExistsException("The order with id " + orderId + " does not exist."));
        double weight = order.getWeight();

        log.info("Async thread {} started processing order {}",
                Thread.currentThread().getName(), orderId);

        try {

            order.setStatus(OrderStatus.PROCESSING);

            ShippingStrategy strategy = shippingStrategyContext.getStrategy(order.getShippingType());
            order.setCost(strategy.calculateCost(weight));
            log.info("Set cost for order with id {} to {}", orderId, order.getCost());
            orderRepository.save(order);

            Thread.sleep(asyncProcessDelay);

            order.setStatus(OrderStatus.COMPLETED);
            log.info("Order {} completed successfully.", orderId);
        }

        catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread was interrupted while calculating cost for order with id {}, so the order is delayed. Exception: {}", orderId, e.getMessage());
            order.setStatus(OrderStatus.DELAYED);
        }

        catch(Exception e) {
            log.error("Failed to set cost for order with id {}. Exception: {}", orderId, e.getMessage());
            order.setStatus(OrderStatus.FAILED);
            throw new FailedCostCalculationException("Failed to calculate cost for order with id " + orderId);
        }
        finally {
            orderRepository.save(order);
            log.info("Order {} saved with status {} and cost {}",
                    order.getId(), order.getStatus(), order.getCost());
        }

    }

}
