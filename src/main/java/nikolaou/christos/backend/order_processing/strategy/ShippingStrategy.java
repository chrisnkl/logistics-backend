package nikolaou.christos.backend.order_processing.strategy;

public interface ShippingStrategy {

    double normalCost = 2.5;

    double calculateCost(double weight);

}
