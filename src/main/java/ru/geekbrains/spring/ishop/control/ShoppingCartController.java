package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.ProductService;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@RequestMapping("/cart")
public class ShoppingCartController {
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
////        ShoppingCart cart;
////        if (session.getAttribute("cart") == null) {
////            cart = new ShoppingCart();
////            session.setAttribute("cart", cart);
////        } else {
////            cart = (ShoppingCart) session.getAttribute("cart");
////        }
//
//        model.addAttribute("cart", getShoppingCartForSession(session));
//        return "amin/cart";
//    }
//    @GetMapping
//    public String cartPage(Model model, HttpSession session) {
//        model.addAttribute("cart", cartService.getShoppingCartForSession(session));
//        return "amin/cart";
//    }
    @GetMapping
    public String cartPage(Model model, HttpSession session) {
        model.addAttribute("cart", cartService.getShoppingCartForSession(session));
        categoryService.addToModelAttributeCategories(model);
        model.addAttribute("activePage", "Cart");
        return "amin/cart";
    }

//    @GetMapping("/add/{prod_id}/prod_id")
//    public void addToCart(@PathVariable Long prod_id, HttpSession session) throws Throwable {
//        ShoppingCart cart = getShoppingCartForSession(session);
//        Product product = productService.findById(prod_id);
//        cart.addItem(product);
//    }
    @GetMapping("/add/{prod_id}/prod_id")
    public String addProductToCart(@PathVariable Long prod_id, HttpServletRequest httpServletRequest) throws Throwable {
        cartService.addToCart(httpServletRequest.getSession(), prod_id);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

    //TODO for a future
//    @GetMapping("/add/{prod_id}/prod_id/{quantity}/quantity")
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
    public String removeFromCart(HttpServletRequest httpServletRequest) throws Throwable {
        cartService.clearCart(httpServletRequest.getSession());
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

//    private ShoppingCart getShoppingCartForSession(HttpSession session) {
//        ShoppingCart cart;
//        if (session.getAttribute("cart") == null) {
//            cart = new ShoppingCart();
//            session.setAttribute("cart", cart);
//        } else {
//            cart = (ShoppingCart) session.getAttribute("cart");
//        }
//        return cart;
//    }
}
