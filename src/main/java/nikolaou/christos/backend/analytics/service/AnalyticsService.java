package nikolaou.christos.backend.analytics.service;

import lombok.RequiredArgsConstructor;
import nikolaou.christos.backend.analytics.dto.AnalyticsResponse;
import nikolaou.christos.backend.order_processing.dto.OrderResponse;
import nikolaou.christos.backend.order_processing.model.Order;
import nikolaou.christos.backend.order_processing.repository.OrderRepository;
import nikolaou.christos.backend.order_processing.utils.OrderStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final OrderRepository orderRepository;

    @Async
    public CompletableFuture<AnalyticsResponse> getAnalytics() {

        List<Order> orders = orderRepository.findAll();

        double revenue = orders.stream()
                .mapToDouble(Order::getCost)
                .sum();

        Map<String, List<OrderResponse>> groupedByDestination = orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.groupingBy(OrderResponse::destination));

        OrderResponse mostExpensiveOrder = orders.stream()
                .max(Comparator.comparingDouble(Order::getCost))
                .map(OrderResponse::new)
                .orElse(null);

        int totalOrders = orders.size();

        long totalCompletedOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                .count();

        long totalFailedOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.FAILED)
                .count();

        long totalDelayedOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELAYED)
                .count();

        long totalPendingOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.PENDING)
                .count();



        AnalyticsResponse response = new AnalyticsResponse(
                revenue,
                groupedByDestination,
                mostExpensiveOrder,
                totalOrders,
                totalCompletedOrders,
                totalFailedOrders,
                totalDelayedOrders,
                totalPendingOrders

        );

        return CompletableFuture.completedFuture(response);
    }


}
