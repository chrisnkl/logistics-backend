package nikolaou.christos.backend.order_processing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nikolaou.christos.backend.order_processing.dto.OrderRequest;
import nikolaou.christos.backend.order_processing.utils.OrderStatus;
import nikolaou.christos.backend.order_processing.utils.ShippingType;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customerName", nullable = false)
    private String customerName;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(name = "shippingType", nullable = false)
    private ShippingType shippingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "cost", nullable = false)
    private double cost;

    public Order(OrderRequest orderRequest) {
        if(orderRequest.id() != null && orderRequest.id() > 0) this.id = orderRequest.id();
        this.customerName = orderRequest.customerName();
        this.weight = orderRequest.weight();
        this.destination = orderRequest.destination();
        this.shippingType = orderRequest.shippingType();
        this.status = OrderStatus.PENDING;
        this.cost = 0.0;
    }

}
