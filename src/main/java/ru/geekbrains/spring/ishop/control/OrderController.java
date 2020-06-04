package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.OrderService;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpServletRequest;

@Component
@RequestMapping("/profile/order")
public class OrderController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private ShoppingCartService cartService;

    @Autowired
    public void setShoppingCartService(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/create")
    public RedirectView createOrder(Model model, HttpServletRequest httpServletRequest) {
        ShoppingCart cart = cartService.getShoppingCartForSession(
                httpServletRequest.getSession());
        if(orderService.create(cart, httpServletRequest.getSession())) {
            cartService.clearCart(httpServletRequest.getSession());

            //TODO скорее всего не передастся при редиректе
            model.addAttribute("orderCreated", "The Order Has Been Created Successfully");

            return new RedirectView("/amin/profile/order/all");
        } else {
            //TODO скорее всего не передастся при редиректе
            model.addAttribute("orderCreated", "Something Wrong With The Order Creating!");
            //FIXME переделать на с моделью
            // url считается от корня, чтобы получилась:
            // http://localhost:8080/amin/profile/cart
            return new RedirectView("/amin/profile/cart");
        }

    }
    //https://www.baeldung.com/spring-redirect-and-forward
    //return new ModelAndView("redirect:/redirectedUrl", model);

    @GetMapping("/add/{prod_id}/prod_id")
    public String addProductToCart(@PathVariable Long prod_id, HttpServletRequest httpServletRequest) throws Throwable {
        cartService.addToCart(httpServletRequest.getSession(), prod_id);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

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
