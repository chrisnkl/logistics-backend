package nikolaou.christos.backend.fleet_management.dto;

import nikolaou.christos.backend.fleet_management.model.Vehicle;
import nikolaou.christos.backend.fleet_management.utils.VehicleType;

public record VehicleResponse(Long id, VehicleType vehicleType, String licensePlate, double capacity, int maxSpeed) {

    public VehicleResponse(Vehicle vehicle) {
        this(vehicle.getId(), vehicle.getType(), vehicle.getLicensePlate(), vehicle.getCapacity(), vehicle.getMaxSpeed());
    }

}
