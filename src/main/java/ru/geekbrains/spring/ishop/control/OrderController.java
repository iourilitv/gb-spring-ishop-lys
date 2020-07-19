package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.rabbit.RabbitSender;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.OrderService;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.SystemDelivery;
import ru.geekbrains.spring.ishop.utils.SystemOrder;
import ru.geekbrains.spring.ishop.utils.filters.OrderFilter;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Component
@RequestMapping("/profile/order")
public class OrderController {
    private final CategoryService categoryService;
    private final ShoppingCartService cartService;
    private final OrderService orderService;
    private final OrderFilter orderFilter;
    private final RabbitSender rabbitSender;

    @Autowired
    public OrderController(CategoryService categoryService, ShoppingCartService cartService,
                           OrderService orderService, OrderFilter orderFilter,
                           RabbitSender rabbitSender) {
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.orderFilter = orderFilter;
        this.rabbitSender = rabbitSender;
    }

    @GetMapping("/all")
    public String allOrders(@RequestParam Map<String, String> params,
                            Model model, HttpSession session) {
        //удаляем атрибут заказа
        session.removeAttribute("order");
        //инициируем настройки фильтра
        orderFilter.init(params);
        //получаем объект страницы с применением фильтра
        //TODO created_at -> константы
        Page<Order> page = orderService.findAll(orderFilter,"createdAt");
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", orderFilter.getFilterDefinition());
        //коллекцию категорий
        categoryService.addToModelAttributeCategories(model);
        //объект страницы заказов
        model.addAttribute("page", page);
        //активную страницу
        model.addAttribute("activePage", "Profile");
        //коллекцию статусов заказа
        model.addAttribute("orderStatuses", orderService.findAllOrderStatuses());

        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);

        //вызываем файл orders.html
        return "amin/orders";
    }

    @GetMapping("/proceedToCheckout")
    public RedirectView proceedToCheckoutOrder() {
        //отправляем сообщение в RabbitReceiver в другом сервисе
        try {
            rabbitSender.sendMessage("Create");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RedirectView("/amin/profile/order/show/0/order_id");
    }

    @GetMapping("/rollBack")
    public RedirectView proceedToRollBackToCart(HttpSession session) {
        orderService.rollBackToCart(session);
        return new RedirectView("/amin/profile/cart");
    }

    @GetMapping("/create")
    public RedirectView createOrder(HttpSession session) {
        SystemOrder systemOrder = (SystemOrder) session.getAttribute("order");
        Order order = orderService.saveNewOrder(systemOrder);
        if(order != null && orderService.isOrderSavedCorrectly(order, systemOrder)) {
            cartService.getClearedCartForSession(session);
            session.removeAttribute("order");
            return new RedirectView("/amin/profile/order/all");
        }
        return new RedirectView("/amin/profile/order/rollBack");
    }

    @GetMapping("/show/{order_id}/order_id")
    public String showOrderDetails(@PathVariable Long order_id, ModelMap model,
                                   HttpSession session){
        SystemOrder systemOrder = orderService.getSystemOrderForSession(session, order_id);

        model.addAttribute("order", systemOrder);
        model.addAttribute("delivery", systemOrder.getSystemDelivery());

        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);

        return "amin/order-details";
    }

//    @GetMapping("/edit/{order_id}/order_id")
//    public String editOrder(@PathVariable Long order_id, Model model,
//                            HttpSession session) {
//        SystemOrder systemOrder = orderService.getSystemOrderForSession(session, order_id);
//        model.addAttribute("order", systemOrder);
//        model.addAttribute("orderStatuses", orderService.findAllOrderStatuses());
//        model.addAttribute("orderStatus", systemOrder.getOrderStatus());
//        model.addAttribute("delivery", systemOrder.getSystemDelivery());
//
//        ShoppingCart cart = cartService.getShoppingCartForSession(session);
//        //добавляем общее количество товаров в корзине
//        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
//        model.addAttribute("cartItemsQuantity", cartItemsQuantity);
//
//        return "amin/order-form";
//    }
//
//    @GetMapping("/delete/{order_id}/order_id")
//    public RedirectView removeOrder(@PathVariable("order_id") Long orderId) {
//        orderService.delete(orderId);
//        return new RedirectView("/amin/profile/order/all");
//    }
//
//    @GetMapping("/cancel/{order_id}/order_id")
//    public RedirectView cancelOrder(@PathVariable("order_id") Long orderId) {
//        orderService.cancelOrder(orderId);
//        return new RedirectView("/amin/profile/order/all");
//    }
//
//    @PostMapping("/process/update/orderStatus")
//    public RedirectView processUpdateOrderStatus(@Valid @ModelAttribute("orderStatus") OrderStatus orderStatus,
//                                              BindingResult theBindingResult,
//                                              HttpSession session) {
//        SystemOrder systemOrder = (SystemOrder) session.getAttribute("order");
//        if (!theBindingResult.hasErrors()) {
//            systemOrder.setOrderStatus(orderStatus);
//            orderService.updateOrderStatus(systemOrder);
//        }
//        return new RedirectView("/amin/profile/order/edit/" +
//                systemOrder.getId() + "/order_id");
//    }
//
//    @PostMapping("/process/update/delivery")
//    public RedirectView processUpdateDelivery(@Valid @ModelAttribute("delivery") SystemDelivery systemDelivery,
//                                              BindingResult theBindingResult,
//                                              HttpSession session) {
//        SystemOrder systemOrder = (SystemOrder) session.getAttribute("order");
//        if (!theBindingResult.hasErrors()) {
//            systemOrder.setSystemDelivery(systemDelivery);
//            //сохраняем изменение в БД
//            orderService.updateDelivery(systemOrder);
//        }
//        return new RedirectView("/amin/profile/order/edit/" +
//                systemOrder.getId() + "/order_id");
//    }

}