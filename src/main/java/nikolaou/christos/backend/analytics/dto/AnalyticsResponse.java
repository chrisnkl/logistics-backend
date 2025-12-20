package nikolaou.christos.backend.analytics.dto;

import nikolaou.christos.backend.order_processing.dto.OrderResponse;

import java.util.List;
import java.util.Map;

public record AnalyticsResponse(
        double revenue,
        Map<String, List<OrderResponse>> groupedByDestination,
        OrderResponse mostExpensiveOrder,
        long totalOrders,
        long totalCompletedOrders,
        long totalFailedOrders,
        long totalDelayedOrders,
        long totalPendingOrders
) {
}
