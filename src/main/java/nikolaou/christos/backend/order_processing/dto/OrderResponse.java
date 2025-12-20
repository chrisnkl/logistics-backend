package nikolaou.christos.backend.order_processing.dto;

import nikolaou.christos.backend.order_processing.model.Order;
import nikolaou.christos.backend.order_processing.utils.OrderStatus;
import nikolaou.christos.backend.order_processing.utils.ShippingType;

public record OrderResponse(Long id, String customerName, double weight, String destination, ShippingType shippingType, OrderStatus status, double cost) {

    public OrderResponse(Order order) {
        this(order.getId(), order.getCustomerName(), order.getWeight(), order.getDestination(), order.getShippingType(), order.getStatus(), order.getCost());
    }

}
