package nikolaou.christos.backend.fleet_management.repository;

import nikolaou.christos.backend.fleet_management.model.Vehicle;
import nikolaou.christos.backend.fleet_management.utils.VehicleType;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FleetManagementRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByType(VehicleType type);
}
