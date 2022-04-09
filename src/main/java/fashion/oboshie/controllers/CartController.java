package fashion.oboshie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fashion.oboshie.common.ApiResponse;
import fashion.oboshie.dto.cart.AddToCartDto;
import fashion.oboshie.dto.cart.CartDto;
import fashion.oboshie.exceptionHandling.ProductNotExistException;
import fashion.oboshie.exceptionHandling.CartItemNotExistException;
import fashion.oboshie.models.*;
//import fashion.oboshie.service.AuthenticationService;
//import fashion.oboshie.service.CartService;
//import fashion.oboshie.service.ProductService;
import fashion.oboshie.models.AppUser;
import fashion.oboshie.models.Product;
import fashion.oboshie.services.CartService;
import fashion.oboshie.services.ConfirmationTokenService;
import fashion.oboshie.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private static final String AUTHORIZATION_HEADER_STARTER ="Oboshie";
    private CartService cartService;

    private ProductService productService;

    //@PostMapping("/add")
//    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, HttpServletRequest request){
//        final String authorizationHeader = request.getHeader(AUTHORIZATION);
//
//        if (authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_HEADER_STARTER)){
//            try {
//                String jwtToken = authorizationHeader.substring(AUTHORIZATION_HEADER_STARTER.length());
//                String email = jwtUtil.extractEmail(jwtToken);
//
//                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
//                    AppUser user = (AppUser) this.appUserService.loadUserByUsername(email);
//                    if (jwtUtil.validateToken(jwtToken, user)){
//                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, null, user.getAuthorities());
//                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                    }
//                }
//                filterChain.doFilter(request, response);
//            }catch (Exception e){
//                log.error("Error logging in: {}", e.getMessage());
//                response.setHeader("error", e.getMessage());
//                response.setStatus(FORBIDDEN.value());
////                    response.sendError(FORBIDDEN.value());
//                Map<String, String> error = Map.of("error_message", e.getMessage());
//                response.setContentType(APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(), error);
//            }
//        }else{
//            filterChain.doFilter(request, response);
//        }
//        cartService.addToCart(addToCartDto, product, appUser);
//        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
//
//    }
//    @GetMapping("/")
//    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {
//        authenticationService.authenticate(token);
//        AppUser appUser = authenticationService.getUser(token);
//        CartDto cartDto = cartService.listCartItems(appUser);
//        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
//    }
//    @PutMapping("/update/{cartItemId}")
//    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto,
//                                                      @RequestParam("token") String token) throws AuthenticationFailException,ProductNotExistException {
//        authenticationService.authenticate(token);
//        AppUser appUser = authenticationService.getappUser(token);
//        Product product = productService.getProductById(cartDto.getProductId());
//        cartService.updateCartItem(cartDto, appUser,product);
//        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/delete/{cartItemId}")
//    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int itemID,@RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException {
//        authenticationService.authenticate(token);
//        AppUser appUser = authenticationService.getAppUser(token).getId();
//        cartService.deleteCartItem(itemID, appUser);
//        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
//    }

}

