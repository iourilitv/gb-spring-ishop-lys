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
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.OrderService;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.SystemOrder;
import ru.geekbrains.spring.ishop.utils.filters.OrderFilter;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Component
@RequestMapping("/profile/order")
public class OrderController {
    private final CategoryService categoryService;
    private final ShoppingCartService cartService;
    private final OrderService orderService;
    private final OrderFilter orderFilter;

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

        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);

        //вызываем файл orders.html
        return "amin/orders";
    }

//    @GetMapping("/proceedToCheckout")
//    public String proceedToCheckoutOrder(Model model, HttpSession session) {
//        SystemOrder systemOrder = orderService.createSystemOrder(session);
//        model.addAttribute("order", systemOrder);
//        model.addAttribute("delivery", systemOrder.getSystemDelivery());
//
//        ShoppingCart cart = cartService.getShoppingCartForSession(session);
//        //добавляем общее количество товаров в корзине
//        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
//        model.addAttribute("cartItemsQuantity", cartItemsQuantity);
//
//        return "amin/order-details";
//    }
    @GetMapping("/proceedToCheckout")
    public RedirectView proceedToCheckoutOrder(Model model, HttpSession session) {
        return new RedirectView("/amin/profile/order/show/0/order_id");
    }

    @GetMapping("/rollBack")
    public RedirectView proceedToRollBackToCart(Model model, HttpSession session) {
        orderService.rollBackToCart(session);
        return new RedirectView("/amin/profile/cart");
    }

//    @GetMapping("/create")
//    public RedirectView createOrder(Model model, HttpServletRequest httpServletRequest) {
//        HttpSession session = httpServletRequest.getSession();
//        ShoppingCart cart = cartService.getShoppingCartForSession(session);
//        if(orderService.create(cart, session)) {
//            cartService.clearCart(session);
//            Order order = (Order)session.getAttribute("order");
//            return new RedirectView("/amin/profile/order/show/" +
//                    order.getId() + "/order_id");
//        } else {
//            //TODO скорее всего не передастся при редиректе
//            model.addAttribute("orderCreated", "Something Wrong With The Order Creating!");
//            //FIXME переделать на с моделью
//            // url считается от корня, чтобы получилась:
//            // http://localhost:8080/amin/profile/cart
//            return new RedirectView("/amin/profile/cart");
//        }
//    }
    @GetMapping("/create")
    public RedirectView createOrder(Model model, HttpSession session) {
        if(orderService.save((SystemOrder) session.getAttribute("order"))) {
            cartService.getClearedCartForSession(session);
            session.removeAttribute("order");
            return new RedirectView("/amin/profile/order/all");
        }
        return new RedirectView("/amin/profile/rollBack");
    }

    @GetMapping("/show/{order_id}/order_id")
    public String showOrderDetails(@PathVariable Long order_id, ModelMap model,
                                   HttpSession session){
        //TODO наполнить модель атрибутами, в т.ч. editable=false
        // в order-details добавить кнопку "Edit Order" на edit/{order_id}/order_id
//        Order order = orderService.findById(order_id);
//        model.addAttribute("order", order);
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
//        Order order = orderService.findById(order_id);
//        model.addAttribute("order", order);
//        List<OrderStatus> orderStatuses = orderService.findAllOrderStatuses();
//        model.addAttribute("orderStatuses", orderStatuses);
//
//        ShoppingCart cart = cartService.getShoppingCartForSession(session);
//        //добавляем общее количество товаров в корзине
//        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
//        model.addAttribute("cartItemsQuantity", cartItemsQuantity);
//
//        return "amin/order-form";
//    }
    @GetMapping("/edit/{order_id}/order_id")
    public RedirectView editOrder(@PathVariable Long order_id, Model model,
                            HttpSession session) {
        //TODO Temporarily
        System.out.println("********* Edit Order **********");
        System.out.println(session.getAttribute("order"));
        //TODO temporarily - вести на форму заказа
        return new RedirectView("/amin/profile/order/all");
    }

    @GetMapping("/delete/{order_id}/order_id")
    public RedirectView removeOrder(@PathVariable("order_id") Long orderId) {
        orderService.delete(orderId);
        return new RedirectView("/amin/profile/order/all");
    }

    @GetMapping("/cancel/{order_id}/order_id")
    public RedirectView cancelOrder(@PathVariable("order_id") Long orderId) {
        orderService.cancelOrder(orderId);
        return new RedirectView("/amin/profile/order/all");
    }

    // Binding Result после @ValidModel !!!
    @PostMapping("/process/edit/{order_id}/order_id")
    public RedirectView processEditOrder(@PathVariable("order_id") Long orderId,
            @Valid @ModelAttribute("order") SystemOrder order,
                                   BindingResult theBindingResult) {
//        System.out.println(order);

        orderService.save(order);
        return new RedirectView("/amin/profile/order/all");
    }

    @GetMapping("/edit/{order_id}/order_id/update/{prod_id}/prod_id")
    public Object editOrderItem(@PathVariable("order_id") Long orderId,
                                @PathVariable("prod_id") Long prodId,
                                Model model, HttpSession session) {
        List<OrderItem> orderItems = orderService.findById(orderId).getOrderItems();
        model.addAttribute("orderId", orderId);
        OrderItem orderItem = orderService.findOrderItemByProdId(orderItems, prodId);
        model.addAttribute("orderItem", orderItem);

        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);

        return "amin/order-item-form";
    }

    @PostMapping("/process/edit/{order_id}/order_id/update/{prod_id}/prod_id")
    public String processEditOrderItem(
            @PathVariable("order_id") Long orderId,
            @PathVariable("prod_id") Long prodId,
            @Valid @ModelAttribute("orderItem") OrderItem orderItem,
            BindingResult theBindingResult, Model model) {
        Order order = orderItem.getOrder();
        order = orderService.recalculateOrderCosts(order, orderItem);
        model.addAttribute("order", order);
        List<OrderStatus> orderStatuses = orderService.findAllOrderStatuses();
        model.addAttribute("orderStatuses", orderStatuses);
        return "amin/order-form";
    }

}