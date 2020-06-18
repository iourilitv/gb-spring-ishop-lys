package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.websocket.Request;
import ru.geekbrains.spring.ishop.utils.websocket.Response;

@Controller
public class WebSocketController {
    private ShoppingCartService cartService;

    @Autowired
    public void setShoppingCartService(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    @MessageMapping("/add-to-cart")
    @SendTo("/topic/add-to-cart")
    public Response sendAddToCartResponse(Request request) throws Throwable {
        Long prodId = Long.valueOf(request.getParam());
        int quantity = cartService.addToCart(cartService.getCart(), prodId);
        int cartItemsQuantity = cartService.getCartItemsQuantity(cartService.getCart());
        return new Response(request.getParam(), String.valueOf(quantity), cartItemsQuantity);
    }

}
