package fashion.oboshie.repositories;

import fashion.oboshie.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,long> {
}
