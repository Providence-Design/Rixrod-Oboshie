package fashion.oboshie.services;

import fashion.oboshie.dto.cart.CartDto;
import fashion.oboshie.dto.checkOut.CheckOutItemDto;
import fashion.oboshie.dto.cart.CartItemDto;
import fashion.oboshie.exceptionHandling.OrderNotFoundException;
import fashion.oboshie.models.Order;
import fashion.oboshie.models.OrderItem;
import fashion.oboshie.models.AppUser;
import fashion.oboshie.repositories.OrderItemRepository;
import fashion.oboshie.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private CartService cartService;
    OrderRepository orderRepository;

    OrderItemRepository orderItemRepository;

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

//    // create total price
//    SessionCreateParams.LineItem.PriceData createPriceData(CheckOutItemDto checkOutItemDto) {
//        return SessionCreateParams.LineItem.PriceData.builder()
//                .setCurrency("usd")
//                .setUnitAmount((int)(checkOutItemDto.getPrice()*100))
//                .setProductData(
//                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                .setName(checkOutItemDto.getProductName())
//                                .build())
//                .build();
//    }
//
//    // build each product in the stripe checkout page
//    SessionCreateParams.LineItem createSessionLineItem(CheckOutItemDto checkOutItemDto) {
//        return SessionCreateParams.LineItem.builder()
//                // set price for each product
//                .setPriceData(createPriceData(checkOutItemDto))
//                // set quantity for each product
//                .setQuantity(Long.parseLong(String.valueOf(checkOutItemDto.getQuantity())))
//                .build();
//    }
//
//    // create session from list of checkout items
//    public Session createSession(List<CheckOutItemDto> checkOutItemDtoList) throws StripeException {
//
//        // supply success and failure url for stripe
//        String successURL = baseURL + "payment/success";
//        String failedURL = baseURL + "payment/failed";
//
//        // set the private key
//        Stripe.apiKey = apiKey;
//
//        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<>();
//
//        // for each product compute SessionCreateParams.LineItem
//        for (CheckOutItemDto checkOutItemDto : checkOutItemDtoList) {
//            sessionItemsList.add(createSessionLineItem(checkOutItemDto));
//        }
//
//        // build the session param
//        SessionCreateParams params = SessionCreateParams.builder()
//                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
//                .setMode(SessionCreateParams.Mode.PAYMENT)
//                .setCancelUrl(failedURL)
//                .addAllLineItem(sessionItemsList)
//                .setSuccessUrl(successURL)
//                .build();
//        return Session.create(params);
//    }
//
//    public void placeOrder(AppUser appUser, String sessionId) {
//        // first let get cart items for the user
//        CartDto cartDto = cartService.listCartItems(appUser);
//
//        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();
//
//        // create the order and save it
//        Order newOrder = new Order();
//        newOrder.setCreatedDate(new Date());
//        newOrder.setSessionId(sessionId);
//        newOrder.setAppUser(appUser);
//        newOrder.setTotalPrice(cartDto.getTotalCost());
//        orderRepository.save(newOrder);
//
//        for (CartItemDto cartItemDto : cartItemDtoList) {
//            // create orderItem and save each one
//            OrderItem orderItem = new OrderItem();
//            orderItem.setCreatedDate(new Date());
//            orderItem.setPrice(cartItemDto.getProduct().getPrice());
//            orderItem.setProduct(cartItemDto.getProduct());
//            orderItem.setQuantity(cartItemDto.getQuantity());
//            orderItem.setOrder(newOrder);
//            // add to order item list
//            orderItemRepository.save(orderItem);
//        }
//        //
//        cartService.deleteUserCartItems(appUser);
//    }

    public List<Order> listOrders(AppUser appUser) {
        return orderRepository.findAllByUserOrderByCreatedDateDesc(appUser);
    }


    public Order getOrder(Integer orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderNotFoundException("Order not found");
    }
}
