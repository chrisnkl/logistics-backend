package nikolaou.christos.backend.order_processing.strategy.impl;

import nikolaou.christos.backend.order_processing.strategy.ShippingStrategy;

public class StandardShippingStrategy implements ShippingStrategy {

    @Override
    public double calculateCost(double weight) {
        return weight * normalCost;
    }
}
