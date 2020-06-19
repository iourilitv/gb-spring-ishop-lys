package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.service.UserService;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;
import ru.geekbrains.spring.ishop.utils.SystemUser;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    private UserService userService;
    private ShoppingCartService cartService;

    @Autowired
    public UserProfileController(UserService userService, ShoppingCartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    public String showProfilePage(HttpSession session, Model model) {
        User theUser = (User) session.getAttribute("user");
        model.addAttribute("systemUser", new SystemUser(theUser));

        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);

        return "amin/profile";
    }

}
