package nikolaou.christos.backend.fleet_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import lib.response.BackendResponse;
import nikolaou.christos.backend.fleet_management.dto.AllVehiclesResponse;
import nikolaou.christos.backend.fleet_management.dto.VehicleRequest;
import nikolaou.christos.backend.fleet_management.dto.VehicleResponse;
import nikolaou.christos.backend.fleet_management.factory.VehicleFactory;
import nikolaou.christos.backend.fleet_management.model.Vehicle;
import nikolaou.christos.backend.fleet_management.service.FleetManagementService;
import nikolaou.christos.backend.fleet_management.utils.VehicleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = FleetManagementController.class,
excludeAutoConfiguration =  {SecurityAutoConfiguration.class})
class FleetManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Inject into spring application context
    private FleetManagementService fleetManagementService;

    @MockitoBean // Inject into spring application context
    private VehicleFactory vehicleFactory;

    @Test
    void testInit_returns200() throws Exception {

        // Arrange
        Long vehicleId = 1L;
        VehicleType vehicleType = VehicleType.TRUCK;
        String licensePlate = "license-plate";
        double capacity = 1500;
        int maxSpeed = 80;

        VehicleRequest mockRequest = new VehicleRequest(vehicleType,licensePlate,capacity,maxSpeed);
        Vehicle mockVehicle = new Vehicle(vehicleId, vehicleType, licensePlate, capacity, maxSpeed);

        when(vehicleFactory.createVehicle(vehicleType, licensePlate, capacity, maxSpeed)).thenReturn(mockVehicle);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders

                .post("/vehicles/init")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRequest))

        ).andReturn();

        BackendResponse<VehicleResponse> backendResponse = new ObjectMapper()
                .readValue(result.getResponse().getContentAsString(),
                        new ObjectMapper().getTypeFactory().constructParametricType(BackendResponse.class, VehicleResponse.class));

        VehicleResponse vehicleResponse = backendResponse.data();

        // Assert
        assertNotNull(backendResponse);
        assertNotNull(vehicleResponse);
        assertEquals(backendResponse.message(), "Vehicle created successfully.", () -> "The backend response message is not the same as the actual message.");
        assertEquals(mockRequest.licensePlate(), vehicleResponse.licensePlate());
        assertEquals(mockRequest.vehicleType(), vehicleResponse.vehicleType());
        assertEquals(mockRequest.capacity(), vehicleResponse.capacity());
        assertEquals(mockRequest.maxSpeed(), vehicleResponse.maxSpeed());

    }

    @Test
    @DisplayName("testGetAllVehicles_success_returnsPage")
    void testGetAllVehicles_whenSuccessful_returnsAllVehiclesPage() throws Exception {

        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        VehicleResponse vehicle1 = new VehicleResponse(1L, VehicleType.TRUCK, "Truck", 5000, 80);
        VehicleResponse vehicle2 = new VehicleResponse(2L, VehicleType.VAN, "Van", 1000, 100);

        List<VehicleResponse> vehicles = List.of(vehicle1, vehicle2);
        Page<VehicleResponse> pageResult =
                new PageImpl<>(vehicles, pageable, 2);

        when(fleetManagementService.getAllVehicles(any())).thenReturn(pageResult);

        // Act
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/vehicles")
                                .param("page", "0")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        BackendResponse<AllVehiclesResponse> backendResponse =
                new ObjectMapper().readValue(
                        response,
                        new ObjectMapper().getTypeFactory().constructParametricType(BackendResponse.class, AllVehiclesResponse.class));

        AllVehiclesResponse allVehiclesResponse = backendResponse.data();

        // Assert
        assertNotNull(backendResponse);
        assertNotNull(allVehiclesResponse);
        assertEquals(HttpStatus.OK.value(), backendResponse.status());
        assertEquals("Vehicles retrieved successfully.", backendResponse.message());

        assertEquals(vehicles.size(), allVehiclesResponse.vehicles().size());
        assertEquals(1, allVehiclesResponse.totalPages());
        assertEquals(vehicles.size(), allVehiclesResponse.totalElements());

        assertEquals("Truck", allVehiclesResponse.vehicles().getFirst().licensePlate());
        assertEquals("Van", allVehiclesResponse.vehicles().getLast().licensePlate());

    }

}