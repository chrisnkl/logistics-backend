package nikolaou.christos.backend.fleet_management.service;

import lombok.RequiredArgsConstructor;
import nikolaou.christos.backend.fleet_management.repository.FleetManagementRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FleetManagementService {

    private final FleetManagementRepository fleetManagementRepository;

}
