package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;
import ru.geekbrains.spring.ishop.utils.websocket.Request;
import ru.geekbrains.spring.ishop.utils.websocket.Response;

@Controller
public class WebSocketController {
    private ShoppingCartService cartService;

    @Autowired
    public void setShoppingCartService(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

//    @GetMapping("/shop")
//    public String showShopPage(Model model, HttpSession session) {
//        cart = cartService.getShoppingCartForSession(session);
//        model.addAttribute("cart", cart);
////        model.addAttribute("prodId", 10L);
//        return "/shop-page";
//    }

    //слушаем адрес /hello и пересылаем на /topic/greetings объект Greeting
//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Greeting greeting(Message message) throws Throwable {
//        Long prodId = Long.valueOf(message.getName());
//        int quantity = cartService.addToCart(cart, prodId);
//
//        System.out.println("quantity=" + quantity);
//
//        return new Greeting("(" + quantity + ")");
//    }

//    @MessageMapping("/add-to-cart")
//    @SendTo("/topic/add-to-cart")
//    public Response sendResponse(Request request) throws Throwable {
//        Long prodId = Long.valueOf(request.getParam());
//        int quantity = cartService.addToCart(cartService.getCart(), prodId);
//
//        System.out.println("prodId:" + prodId + ", quantity=" + quantity);
//
////        return new Response("(" + quantity + ")");
//        return new Response(String.valueOf(quantity));
//    }
    @MessageMapping("/add-to-cart")
    @SendTo("/topic/add-to-cart")
    public Response sendAddToCartResponse(Request request) throws Throwable {
        Long prodId = Long.valueOf(request.getParam());
        int quantity = cartService.addToCart(cartService.getCart(), prodId);
        int cartItemsQuantity = cartService.getCartItemsQuantity(cartService.getCart());

        System.out.println("quantity=" + quantity);

        return new Response(request.getParam(), String.valueOf(quantity), cartItemsQuantity);
    }

}
