package nikolaou.christos.backend.fleet_management.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import nikolaou.christos.backend.fleet_management.utils.VehicleType;

public record VehicleRequest(@Enumerated(EnumType.STRING) VehicleType vehicleType, String licensePlate, double capacity, int maxSpeed) {
}
