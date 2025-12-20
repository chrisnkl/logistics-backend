package nikolaou.christos.backend.fleet_management.dto;

import java.util.List;

public record AllVehiclesResponse(List<VehicleResponse> vehicles, int totalPages, int totalElements) {
}
