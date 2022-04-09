package fashion.oboshie.services;

import java.util.List;
import javax.transaction.Transactional;

import fashion.oboshie.models.AppUser;
import fashion.oboshie.models.WishList;
import fashion.oboshie.repositories.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class WishListService {

    private final WishListRepository wishListRepository;

    public void createWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<WishList> readWishList(AppUser appUser) {
        return wishListRepository.findAllByUserIdOrderByCreatedDateDesc(appUser);
    }
}
