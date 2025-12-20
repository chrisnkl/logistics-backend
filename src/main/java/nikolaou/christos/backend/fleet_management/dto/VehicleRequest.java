package nikolaou.christos.backend.fleet_management.dto;

import jakarta.validation.constraints.NotNull;

public record VehicleRequest(@NotNull Long id) {
}
