package nikolaou.christos.backend.fleet_management.service;

import nikolaou.christos.backend.fleet_management.dto.VehicleResponse;
import nikolaou.christos.backend.fleet_management.model.Vehicle;
import nikolaou.christos.backend.fleet_management.repository.FleetManagementRepository;
import nikolaou.christos.backend.fleet_management.utils.VehicleType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FleetManagementServiceTest {

    @Mock
    private FleetManagementRepository repository;

    @InjectMocks
    private FleetManagementService service;

    @Test
    @Disabled
    void testGetAllVehicles_returnAllVehicles() {

        // Arrange
        Pageable pageable = PageRequest.of(0,10);

        VehicleResponse vehicle1 = new VehicleResponse(1L, VehicleType.TRUCK, "Truck", 5000, 80);
        VehicleResponse vehicle2 = new VehicleResponse(2L, VehicleType.VAN, "Van", 1000, 100);

        List<VehicleResponse> vehicles = List.of(vehicle1, vehicle2);
        Page<VehicleResponse> vehiclePage =
                new PageImpl<>(vehicles, pageable, 2);

//        when(repository.findAll(pageable)).thenReturn(vehiclePage);

        // Act
        service.getAllVehicles(pageable);

        // Assert

    }


}