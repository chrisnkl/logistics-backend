package nikolaou.christos.backend.order_processing.strategy;

import jakarta.annotation.PostConstruct;
import nikolaou.christos.backend.order_processing.strategy.impl.ExpressShippingStrategy;
import nikolaou.christos.backend.order_processing.strategy.impl.FreeShippingStrategy;
import nikolaou.christos.backend.order_processing.strategy.impl.StandardShippingStrategy;
import nikolaou.christos.backend.order_processing.utils.ShippingType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ShippingStrategyContext {

    private Map<ShippingType, ShippingStrategy> strategies;

    @PostConstruct
    private void init() {
        this.strategies = new HashMap<>();
        this.strategies.put(ShippingType.FREE, new FreeShippingStrategy());
        this.strategies.put(ShippingType.STANDARD, new StandardShippingStrategy());
        this.strategies.put(ShippingType.EXPRESS, new ExpressShippingStrategy());
    }

    public ShippingStrategy getStrategy(ShippingType type) {
        return strategies.get(type);
    }
}
