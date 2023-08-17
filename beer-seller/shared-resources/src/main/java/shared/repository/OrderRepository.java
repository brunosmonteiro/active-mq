package shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shared.entity.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
