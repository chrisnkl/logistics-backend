package nikolaou.christos.backend.order_processing.strategy;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nikolaou.christos.backend.order_processing.strategy.impl.ExpressShippingStrategy;
import nikolaou.christos.backend.order_processing.strategy.impl.FreeShippingStrategy;
import nikolaou.christos.backend.order_processing.strategy.impl.StandardShippingStrategy;
import nikolaou.christos.backend.order_processing.utils.ShippingType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ShippingStrategyContext {

    private Map<ShippingType, ShippingStrategy> strategies;

    private final FreeShippingStrategy freeStrategy;
    private final StandardShippingStrategy standardStrategy;
    private final ExpressShippingStrategy expressStrategy;


    @PostConstruct
    private void init() {
        this.strategies = new HashMap<>();
        this.strategies.put(ShippingType.FREE, freeStrategy);
        this.strategies.put(ShippingType.STANDARD, standardStrategy);
        this.strategies.put(ShippingType.EXPRESS, expressStrategy);
    }

    public ShippingStrategy getStrategy(ShippingType type) {
        return strategies.get(type);
    }
}
