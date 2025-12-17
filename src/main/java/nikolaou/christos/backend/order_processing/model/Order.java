package nikolaou.christos.backend.order_processing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private String customerName;

    private double weight;

    private String destination;

    private ShippingType shippingType;

    private OrderStatus status;

    private double cost;


}
