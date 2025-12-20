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

    public Vehicle getVehicle(VehicleType vehicleType) {

        return fleetManagementRepository.findByType(vehicleType)
                .orElseThrow(() -> new UnsupportedVehicleTypeException("Unsupported vehicle type."));
    }

}
