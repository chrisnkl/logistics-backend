package nikolaou.christos.backend.fleet_management.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nikolaou.christos.backend.fleet_management.dto.VehicleRequest;
import nikolaou.christos.backend.fleet_management.dto.VehicleResponse;
import nikolaou.christos.backend.fleet_management.exception.UnsupportedVehicleTypeException;
import nikolaou.christos.backend.fleet_management.exception.VehicleNotFoundException;
import nikolaou.christos.backend.fleet_management.repository.FleetManagementRepository;
import nikolaou.christos.backend.fleet_management.utils.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FleetManagementService {

    private final FleetManagementRepository fleetManagementRepository;

    /** Retrieve all vehicles in the fleet.
     * @return a list of VehicleResponse objects.
     */
    public Page<VehicleResponse> getAllVehicles(Pageable pageable) {

        return fleetManagementRepository.findAll(pageable)
                .map(vehicle -> new VehicleResponse(
                        vehicle.getId(),
                        vehicle.getType(),
                        vehicle.getLicensePlate(),
                        vehicle.getCapacity(),
                        vehicle.getMaxSpeed()
                ));
    }

    public VehicleResponse getVehicleByType(VehicleType vehicleType) {
        return fleetManagementRepository.findByType(vehicleType)
                .map(vehicle -> new VehicleResponse(
                        vehicle.getId(),
                        vehicle.getType(),
                        vehicle.getLicensePlate(),
                        vehicle.getCapacity(),
                        vehicle.getMaxSpeed()
                ))
                .orElseThrow(() -> new UnsupportedVehicleTypeException("Vehicle type not found."));
    }

    public VehicleResponse getVehicleById(Long id) {
        return fleetManagementRepository.findById(id)
                .map(VehicleResponse::new)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle with ID " + id + " not found."));
    }

}
