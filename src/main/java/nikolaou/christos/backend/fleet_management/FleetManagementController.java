package nikolaou.christos.backend.fleet_management;

import jakarta.validation.Valid;
import lib.response.BackendResponse;
import lombok.RequiredArgsConstructor;
import nikolaou.christos.backend.fleet_management.dto.AllVehiclesResponse;
import nikolaou.christos.backend.fleet_management.dto.VehicleRequest;
import nikolaou.christos.backend.fleet_management.dto.VehicleResponse;
import nikolaou.christos.backend.fleet_management.factory.VehicleFactory;
import nikolaou.christos.backend.fleet_management.model.Vehicle;
import nikolaou.christos.backend.fleet_management.service.FleetManagementService;
import nikolaou.christos.backend.fleet_management.utils.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class FleetManagementController {

    private final FleetManagementService fleetManagementService;
    private final VehicleFactory vehicleFactory;

    @PostMapping("/init")
    public ResponseEntity<BackendResponse<VehicleResponse>> initializeVehicles(@Valid @RequestBody VehicleRequest vehicleRequest) {

        Vehicle vehicle = vehicleFactory.createVehicle(
                vehicleRequest.vehicleType(),
                vehicleRequest.licensePlate(),
                vehicleRequest.capacity(),
                vehicleRequest.maxSpeed());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new BackendResponse<>(
                        HttpStatus.CREATED.value(),
                        "Vehicle created successfully.",
                        new VehicleResponse(
                                vehicle.getId(),
                                vehicle.getType(),
                                vehicle.getLicensePlate(),
                                vehicle.getCapacity(),
                                vehicle.getMaxSpeed()
                        )
                )
        );

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BackendResponse<AllVehiclesResponse>> getAllVehicles(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<VehicleResponse> vehicles = fleetManagementService.getAllVehicles(pageable);

        return ResponseEntity.ok().body(
                new BackendResponse<>(
                        HttpStatus.OK.value(),
                        "Vehicles retrieved successfully.",
                        new AllVehiclesResponse(vehicles.getContent(), vehicles.getTotalPages(), (int) vehicles.getTotalElements())));

    }

    @GetMapping(value="/type/{vehicleType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BackendResponse<VehicleResponse>> getVehicleByType(@PathVariable VehicleType vehicleType) {

            VehicleResponse vehicleResponse = fleetManagementService.getVehicleByType(vehicleType);

            return ResponseEntity.ok().body(new BackendResponse<>(
                    HttpStatus.OK.value(),
                    "Vehicle was found.",
                    vehicleResponse
            ));
    }

    @GetMapping(value = "/{vehicleId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BackendResponse<VehicleResponse>> getVehicleById(@PathVariable Long vehicleId) {
        VehicleResponse vehicleResponse = fleetManagementService.getVehicleById(vehicleId);
        return ResponseEntity.ok().body(new BackendResponse<>(
                HttpStatus.OK.value(),
                "Vehicle was found.",
                vehicleResponse
        ));
    }


}
