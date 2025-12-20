package nikolaou.christos.backend.order_processing.strategy.impl;

import nikolaou.christos.backend.order_processing.strategy.ShippingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExpressShippingStrategy implements ShippingStrategy {

    @Value("${normal.shipping.cost:2.5}")
    private double normalCost;

    @Override
    public double calculateCost(double weight) {
        return (weight * normalCost)*1.5;
    }
}
