package nikolaou.christos.backend.fleet_management.factory;

import lombok.RequiredArgsConstructor;
import nikolaou.christos.backend.fleet_management.exception.UnsupportedVehicleTypeException;
import nikolaou.christos.backend.fleet_management.model.Vehicle;
import nikolaou.christos.backend.fleet_management.repository.FleetManagementRepository;
import nikolaou.christos.backend.fleet_management.utils.VehicleType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleFactory {

    private final FleetManagementRepository fleetManagementRepository;

    /** Create a new Vehicle instance based on the provided type and attributes.
     * @param type The type of vehicle to create.
     * @param licensePlate The license plate of the vehicle.
     * @param capacity The capacity of the vehicle.
     * @param maxSpeed The maximum speed of the vehicle.
     * @return Vehicle The created Vehicle instance.
     */
    public Vehicle createVehicle(VehicleType type, String licensePlate, double capacity, int maxSpeed) {
        Vehicle vehicle = new Vehicle(type, licensePlate, capacity, maxSpeed);

        return fleetManagementRepository.save(vehicle);
    }

    /**  Retrieve a Vehicle instance based on the provided VehicleType.
     * @param vehicleType The type of vehicle to retrieve.
     * @return The Vehicle instance corresponding to the provided type.
     */
    public Vehicle getVehicle(VehicleType vehicleType) {

        return fleetManagementRepository.findByType(vehicleType)
                .orElseThrow(() -> new UnsupportedVehicleTypeException("Unsupported vehicle type."));
    }

}
