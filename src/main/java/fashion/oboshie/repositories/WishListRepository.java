package fashion.oboshie.repositories;

import fashion.oboshie.models.AppUser;
import fashion.oboshie.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, long> {

    List<WishList> findAllByUserIdOrderByCreatedDateDesc(AppUser appUser);
}
