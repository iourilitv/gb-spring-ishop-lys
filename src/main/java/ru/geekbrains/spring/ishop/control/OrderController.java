package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.services.CategoryService;
import ru.geekbrains.spring.ishop.services.OrderService;
import ru.geekbrains.spring.ishop.services.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.OrderFilter;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
@RequestMapping("/profile/order")
public class OrderController {
    private CategoryService categoryService;
    private ShoppingCartService cartService;
    private OrderService orderService;
    private OrderFilter orderFilter;

    @Autowired
    public OrderController(CategoryService categoryService, ShoppingCartService cartService,
                           OrderService orderService, OrderFilter orderFilter) {
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.orderFilter = orderFilter;
    }

    @GetMapping("/all")
    public String allOrders(@RequestParam Map<String, String> params,
                            Model model, HttpSession session) {
        //инициируем настройки фильтра
        orderFilter.init(params);
        //получаем объект страницы с применением фильтра
        Page<Order> page = orderService.findAll(orderFilter,"createdAt");//TODO created_at -> константы
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", orderFilter.getFilterDefinition());
        //коллекцию категорий
        categoryService.addToModelAttributeCategories(model);
        //объект страницы заказов
        model.addAttribute("page", page);
        //активную страницу
        model.addAttribute("activePage", "Profile");
        //вызываем файл orders.html
        return "amin/orders";
    }

    @GetMapping("/create")
    public RedirectView createOrder(Model model, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        if(orderService.create(cart, session)) {
            cartService.clearCart(session);
            Order order = (Order)session.getAttribute("order");
            return new RedirectView("/amin/profile/order/show/" +
                    order.getId() + "/order_id");
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

    @GetMapping("/show/{order_id}/order_id")
    public String showOrderDetails(@PathVariable Long order_id,  Model model,
                                   HttpServletRequest httpServletRequest){
        //TODO наполнить модель атрибутами, в т.ч. editable=false
        // в order-details добавить кнопку "Edit Order" на edit/{order_id}/order_id
        Order order = orderService.findById(order_id);
        model.addAttribute("order", order);
        return "amin/order-details";
    }

    @GetMapping("/edit/{order_id}/order_id")
    public String editOrder(@PathVariable Long order_id,  HttpServletRequest httpServletRequest) {
        //TODO По идее нужно вести на order-details, но с атрибутом editable=true
        return "amin/order-form";
    }

    @GetMapping("/delete/{order_id}/order_id")
    public String removeOrder(@PathVariable Long order_id,  HttpServletRequest httpServletRequest) {
        orderService.delete(orderService.findById(order_id));
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

    @GetMapping("/cancel/{order_id}/order_id")
    public RedirectView cancelOrder(@PathVariable Long order_id) {
        //TODO просто меням статус на "Canceled" и оставляем заказ в списке
        Order order = orderService.findById(order_id);
        order.setOrderStatus(orderService.findOrderStatusByTitle("Canceled"));
        orderService.save(order);
        return new RedirectView("/amin/profile/order/all");
    }

}
