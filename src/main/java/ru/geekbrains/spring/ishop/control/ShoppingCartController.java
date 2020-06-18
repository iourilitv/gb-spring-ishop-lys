package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;
import ru.geekbrains.spring.ishop.utils.websocket.Request;
import ru.geekbrains.spring.ishop.utils.websocket.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@RequestMapping("/profile/cart")
public class ShoppingCartController {
    private ShoppingCart cart;
    private ShoppingCartService cartService;

    @Autowired
    public void setShoppingCartService(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @GetMapping
//    public String cartPage(Model model, HttpSession session) {
//        model.addAttribute("cart", cartService.getShoppingCartForSession(session));
//        categoryService.addToModelAttributeCategories(model);
//        model.addAttribute("activePage", "Cart");
//        return "amin/cart";
//    }
    @GetMapping
    public String cartPage(Model model, HttpSession session) {
        cart = cartService.getShoppingCartForSession(session);
        model.addAttribute("cart", cart);
        categoryService.addToModelAttributeCategories(model);
        model.addAttribute("activePage", "Cart");
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);
        return "amin/cart";
    }

    //слушаем адрес /hello и пересылаем на /topic/greetings объект Greeting
//    @MessageMapping("/add-to-cart")
//    @SendTo("/topic/add-to-cart")
//    public Response greeting(Request request) throws Throwable {
//        Long prodId = Long.valueOf(request.getParam());
//        int quantity = cartService.addToCart(cart, prodId);
//
//        System.out.println("quantity=" + quantity);
//
//        return new Response("(" + quantity + ")");
//    }

//    @GetMapping("/add/{prod_id}/prod_id")
//    public String addProductToCart(@PathVariable Long prod_id, HttpServletRequest httpServletRequest) throws Throwable {
//        cartService.addToCart(httpServletRequest.getSession(), prod_id);
//        String referrer = httpServletRequest.getHeader("referer");
//        return "redirect:" + referrer;
//    }

    //TODO for a future
//    @GetMapping("/update/{prod_id}/prod_id/{quantity}/quantity")
//    public void updateCartItemQuantity(@PathVariable Long prod_id,
//                          @PathVariable Integer quantity, HttpSession session) {
//    }

    @GetMapping("/delete/{prod_id}/prod_id")
    public String removeFromCart(@PathVariable Long prod_id,  HttpServletRequest httpServletRequest) throws Throwable {
        cartService.removeItemFromCartById(httpServletRequest.getSession(), prod_id);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

    @GetMapping("/clear")
    public String removeFromCart(HttpServletRequest httpServletRequest) {
        cartService.clearCart(httpServletRequest.getSession());
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

}
