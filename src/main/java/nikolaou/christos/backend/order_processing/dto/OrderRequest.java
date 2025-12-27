package nikolaou.christos.backend.order_processing.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import nikolaou.christos.backend.order_processing.utils.ShippingType;

public record OrderRequest(String customerName, double weight, String destination, @Enumerated(EnumType.STRING) ShippingType shippingType) {
}
