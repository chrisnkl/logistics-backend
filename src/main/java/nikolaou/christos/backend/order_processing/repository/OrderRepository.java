package nikolaou.christos.backend.order_processing.repository;

import nikolaou.christos.backend.order_processing.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
