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
import ru.geekbrains.spring.ishop.utils.OrderFilter;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
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
    public String showOrderDetails(@PathVariable Long order_id, ModelMap model,
                                   HttpServletRequest httpServletRequest){
        //TODO наполнить модель атрибутами, в т.ч. editable=false
        // в order-details добавить кнопку "Edit Order" на edit/{order_id}/order_id
        Order order = orderService.findById(order_id);
        model.addAttribute("order", order);
        return "amin/order-details";
    }

    @GetMapping("/edit/{order_id}/order_id")
    public String editOrder(@PathVariable Long order_id, Model model,
                            HttpServletRequest httpServletRequest) {
        Order order = orderService.findById(order_id);
        model.addAttribute("order", order);
        List<OrderStatus> orderStatuses = orderService.findAllOrderStatuses();
        model.addAttribute("orderStatuses", orderStatuses);
        return "amin/order-form";
    }
//    @GetMapping("/edit/{order_id}/order_id")
//    public String editOrder(@PathVariable Long order_id, ModelMap model,
//                            HttpServletRequest httpServletRequest) {
//        HttpSession session = httpServletRequest.getSession();
//        if(session.getAttribute("model") == null) {
//            Order order = orderService.findById(order_id);
//            model.addAttribute("order", order);
//
//            List<OrderStatus> orderStatuses = orderService.findAllOrderStatuses();
//            model.addAttribute("orderStatuses", orderStatuses);
//
//        } else {
//            model = (ModelMap) session.getAttribute("model");
//            model.addAttribute("order", model.getAttribute("order"));
//        }
//        session.setAttribute("model", model);
//        System.out.println("********** model ***********");
//        System.out.println(session.getAttribute("model"));
//        System.out.println("********** order ***********");
//        System.out.println(((Order) Objects.requireNonNull(model.getAttribute("order"))).getId());
//        return "amin/order-form";
//    }

    //DOES NOT WORK
//    @GetMapping("/edit/{order_id}/order_id/update/{prod_id}/prod_id/{quantity}/quantity")
//    public ModelAndView updateOrderItemQuantity(
//            @PathVariable Long order_id, @PathVariable Long prod_id,
//            @PathVariable Integer quantity, HttpSession session) {
//        ModelMap modelMap = (ModelMap) session.getAttribute("model");
//        Order order = (Order) modelMap.getAttribute("order");
//        assert order != null;
//        List<OrderItem> orderItems = order.getOrderItems();
//        BigDecimal totalItemsSum = BigDecimal.ZERO;
//        orderItems.forEach(orderItem -> {
//            if(orderItem.getProduct().getId().equals(prod_id)) {
//                orderItem.setQuantity(quantity);
//                orderItem.setItemCosts(orderItem.getItemPrice()
//                                .multiply(BigDecimal.valueOf(quantity)));
//            }
//            order.setTotalItemsCosts(totalItemsSum.add(orderItem.getItemCosts()));
//        });
////        order.setTotalItemsCosts(totalItemsSum);
//        order.setTotalCosts(order.getTotalItemsCosts().add(order.getDelivery().getDeliveryCost()));
//        System.out.println("******** updateOrderItemQuantity - changing quantity **********");
//        System.out.println(order);
////        modelMap.addAttribute("order", order);
////        session.setAttribute("model", modelMap);
//
////        return new ModelAndView("redirect:/profile/order/edit/" + order.getId() + "/order_id", modelMap);
//        return new ModelAndView("redirect:/profile/order/edit/" + order_id + "/order_id", modelMap);
//    }

//    @GetMapping("/delete/{order_id}/order_id")
//    public String removeOrder(@PathVariable Long order_id,  HttpServletRequest httpServletRequest) {
//        orderService.delete(orderService.findById(order_id));
//        String referrer = httpServletRequest.getHeader("referer");
//        return "redirect:" + referrer;
//    }
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
            @Valid @ModelAttribute("order") Order order,
                                   BindingResult theBindingResult) {
//        System.out.println(order);

        orderService.save(order);
        return new RedirectView("/amin/profile/order/all");
    }

    @GetMapping("/edit/{order_id}/order_id/update/{prod_id}/prod_id")
    public Object editOrderItem(@PathVariable("order_id") Long orderId,
                                @PathVariable("prod_id") Long prodId,
                                Model model,
                                HttpServletRequest httpServletRequest) {
        List<OrderItem> orderItems = orderService.findById(orderId).getOrderItems();
        model.addAttribute("orderId", orderId);
        OrderItem orderItem = orderService.findOrderItemByProdId(orderItems, prodId);
//        OrderItem orderItem = null;
//        for (OrderItem o : orderItems) {
//            if(o.getProduct().getId().equals(prodId)) {
//                orderItem = o;
//            }
//        }
        model.addAttribute("orderItem", orderItem);
        return "amin/order-item-form";
    }

//    @PostMapping("/process/edit/{order_id}/order_id/update/{prod_id}/prod_id")
//    public RedirectView processEditOrderItem(
//            @PathVariable("order_id") Long orderId,
//            @PathVariable("prod_id") Long prodId,
//            @Valid @ModelAttribute("orderItem") OrderItem orderItem,
//            BindingResult theBindingResult, Model model) {
////        System.out.println(model);
////        System.out.println(orderItem);
//        Order order = orderItem.getOrder();
//        orderService.updateOrderDetails(order);
//        model.addAttribute("order", order);
//        return new RedirectView("/amin/profile/order/edit/" +
//                orderId + "/order_id");
//    }
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