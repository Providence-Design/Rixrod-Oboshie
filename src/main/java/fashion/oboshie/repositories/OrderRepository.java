package fashion.oboshie.repositories;

import fashion.oboshie.models.AppUser;
import fashion.oboshie.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, long> {
    List<Order> findAllByUserOrderByCreatedDateDesc(AppUser appUser);
}
