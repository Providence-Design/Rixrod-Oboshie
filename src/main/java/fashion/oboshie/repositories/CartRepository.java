package fashion.oboshie.repositories;

import fashion.oboshie.models.AppUser;
import fashion.oboshie.models.Cart;
import fashion.oboshie.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart, long> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(AppUser appUser);

    List<Cart> deleteByUser(AppUser appUser);


}
