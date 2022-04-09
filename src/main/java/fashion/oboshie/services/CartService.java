package fashion.oboshie.services;

import fashion.oboshie.dto.cart.AddToCartDto;
import fashion.oboshie.dto.cart.CartDto;
import fashion.oboshie.dto.cart.CartItemDto;
import fashion.oboshie.exceptionHandling.CartItemNotExistException;
import fashion.oboshie.models.AppUser;
import fashion.oboshie.models.Cart;
import fashion.oboshie.models.Product;
import fashion.oboshie.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, Product product, AppUser appUser){
        Cart cart = new Cart(product, addToCartDto.getQuantity(), appUser);
        cartRepository.save(cart);
    }


    public CartDto listCartItems(AppUser appUser) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(appUser);
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,totalCost);
    }


    public static CartItemDto getDtoFromCart(Cart cart) {
        return new CartItemDto(cart);
    }


    public void updateCartItem(AddToCartDto cartDto, AppUser appUser,Product product){
        Cart cart = cartRepository.getById((int) cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    public void deleteCartItem(long id,AppUser appUser) throws CartItemNotExistException {
        if (!cartRepository.existsById(id))
            throw new CartItemNotExistException("Cart id is invalid : " + id);
        cartRepository.deleteById(id);

    }

    public void deleteCartItems(AppUser appUser) {
        cartRepository.deleteAll();
    }


    public void deleteUserCartItems(AppUser appUser) {
        cartRepository.deleteByUser(appUser);
    }
}
