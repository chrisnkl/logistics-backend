package nikolaou.christos.backend.fleet_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nikolaou.christos.backend.fleet_management.utils.VehicleType;

@Entity
@Table(name = "vehicles")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private VehicleType type;

    @Column(name = "licensePlate", length = 25, unique = true, nullable = false)
    private String licensePlate;

    @Column(name = "capacity", nullable = false)
    private double capacity;

    @Column(name = "maxSpeed", nullable = false)
    private int maxSpeed;

    public Vehicle(VehicleType type, String licensePlate, double capacity, int maxSpeed) {
        this.type = type;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.maxSpeed = maxSpeed;
    }

}
