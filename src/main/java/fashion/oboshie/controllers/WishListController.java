//package fashion.oboshie.controllers;
//
//import fashion.oboshie.common.ApiResponse;
//import fashion.oboshie.dto.product.ProductDto;
//import fashion.oboshie.models.Product;
//import fashion.oboshie.models.AppUser;
//import fashion.oboshie.models.WishList;
//import fashion.oboshie.services.ConfirmationTokenService;
//import fashion.oboshie.services.ProductService;
//import fashion.oboshie.services.WishListService;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/wishlist")
//public class WishListController {
//
//    private WishListService wishListService;
//
//    private ConfirmationTokenService confirmationTokenService;
//
////    @GetMapping("/{token}")
////    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {
////        int user_id = confirmationTokenService.getAppUser(token).getId();
////        List<WishList> body = wishListService.readWishList(user_id);
////        List<ProductDto> products = new ArrayList<ProductDto>();
////        for (WishList wishList : body) {
////            products.add(ProductService.getDtoFromProduct(wishList.getProduct()));
////        }
////
////        return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
////    }
//
//    @PostMapping("/add")
//    public ResponseEntity<ApiResponse> addWishList(@RequestBody Product product, @RequestParam("token") String token) {
//        confirmationTokenService.saveConfirmationToken(token);
//        AppUser appUser = confirmationTokenService.getAppUser(token);
//        WishList wishList = new WishList(user, product);
//        wishListService.createWishlist(wishList);
//        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Add to wishlist"), HttpStatus.CREATED);
//
//    }
//
//}
