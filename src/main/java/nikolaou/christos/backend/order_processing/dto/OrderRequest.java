package nikolaou.christos.backend.order_processing.dto;

import nikolaou.christos.backend.order_processing.utils.ShippingType;

public record OrderRequest(Long id, String customerName, double weight, String destination, ShippingType shippingType) {
}
