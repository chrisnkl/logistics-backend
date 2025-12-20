package nikolaou.christos.backend.order_processing.strategy.impl;

import nikolaou.christos.backend.order_processing.strategy.ShippingStrategy;
import org.springframework.stereotype.Component;

@Component
public class FreeShippingStrategy implements ShippingStrategy {

    @Override
    public double calculateCost(double weight) {
        return 0.0;
    }
}
