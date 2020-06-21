package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.repository.AddressRepository;
import ru.geekbrains.spring.ishop.repository.OrderItemRepository;
import ru.geekbrains.spring.ishop.repository.OrderRepository;
import ru.geekbrains.spring.ishop.repository.OrderStatusRepository;
import ru.geekbrains.spring.ishop.utils.SystemDelivery;
import ru.geekbrains.spring.ishop.utils.SystemOrder;
import ru.geekbrains.spring.ishop.utils.filters.OrderFilter;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;
import ru.geekbrains.spring.ishop.utils.filters.UtilFilter;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Service
public class OrderService {
    private ShoppingCartService cartService;
    private DeliveryService deliveryService;
    private UserService userService;
    private AddressRepository addressRepository;
    private OrderStatusRepository orderStatusRepository;
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;
    private UtilFilter utilFilter;

//    @Autowired
//    public OrderService(DeliveryService deliveryService, AddressRepository addressRepository,
//                        OrderStatusRepository orderStatusRepository, OrderItemRepository orderItemRepository,
//                        OrderRepository orderRepository, UtilFilter utilFilter) {
//        this.deliveryService = deliveryService;
//        this.addressRepository = addressRepository;
//        this.orderStatusRepository = orderStatusRepository;
//        this.orderItemRepository = orderItemRepository;
//        this.orderRepository = orderRepository;
//        this.utilFilter = utilFilter;
//    }
    @Autowired
    public void setCartService(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Autowired
    public void setOrderStatusRepository(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    @Transactional
    public Page<Order> findAll(OrderFilter filter, String property) {
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(utilFilter.getPageIndex(),
                utilFilter.getLimit(), utilFilter.getDirection(), property);
        return orderRepository.findAll(filter.getSpec(), pageRequest);
    }

    @Transactional
    public Order findById(Long id) {
        return orderRepository.getOne(id);
    }

    public OrderStatus findOrderStatusByTitle(String title) {
        return orderStatusRepository.getOrderStatusByTitleEquals(title);
    }

//    //TODO с ним не сохраняет сущности в бд при даже если нет ошибки отображения страницы
//    // java.lang.StackOverflowError
//    // спринг создает сущности, дает им id и если ошибки нет,
//    // то сохраняет их в бд с этим id. Но при ошибке - уничтожает созданные сущности и
//    // эти id уже не используются!
////    @Transactional
//    public boolean create(ShoppingCart cart, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        Order order = new Order();
//        order.setOrderStatus(orderStatusRepository
//                .getOrderStatusByTitleEquals("Created"));
//        order.setUser(user);
//        order.setTotalItemsCosts(cart.getTotalCost());
//
//        Delivery delivery = deliveryService.getDeliveryForSession(session);
//        delivery.setPhoneNumber(user.getPhoneNumber());
//
//        //TODO temporarily - добавить выбор адреса
////        delivery.setDeliveryAddress(user.getDeliveryAddress());
//        delivery.setDeliveryAddress(addressRepository.getOne(1L));
//        //TODO добавить сервис расчета стоимости доставки
//        delivery.setDeliveryCost(new BigDecimal(100));
//        //TODO добавить ввод значений в интерфейс
//        delivery.setDeliveryExpectedAt(LocalDateTime.of(
//                LocalDate.of(2020, 6, 3),
//                LocalTime.of(14, 20)));
//
//        order.setTotalCosts(order.getTotalItemsCosts().add(delivery.getDeliveryCost()));
//        //Сохраняем сначала заготовку заказа, чтобы получить id
//        orderRepository.save(order);
//        //сохраняем объект доставки уже привязанный к заказу
//        delivery.setOrder(order);
//        deliveryService.save(delivery);
//        //дополняем заказ объектом доставки
//        order.setDelivery(delivery);
//        //сохраняем объекты элементов заказа и добавляем его в заказ
//        order.setOrderItems(saveOrderItems(cart.getCartItems(), order));
//        //окончательно записываем заказ
//        orderRepository.save(order);
//        if(isOrderSavedCorrectly(order, cart)) {
//            session.setAttribute("order", order);
//            return true;
//        }
//        return false;
//    }

//    public SystemOrder createSystemOrder(HttpSession session) {
//        ShoppingCart cart = cartService.getShoppingCartForSession(session);;
//        User user = (User)session.getAttribute("user");
//        SystemDelivery systemDelivery = new SystemDelivery(user.getPhoneNumber(),
//                user.getDeliveryAddress(), BigDecimal.valueOf(100),
//                LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
//        BigDecimal totalCosts = cart.getTotalCost().add(systemDelivery.getDeliveryCost());
//
////        return new SystemOrder(user, cart.getCartItems(),
////                cart.getTotalCost(), totalCosts, systemDelivery);
//        SystemOrder systemOrder = new SystemOrder(user, cart.getCartItems(),
//                cart.getTotalCost(), totalCosts, systemDelivery);
//        session.setAttribute("order", systemOrder);
//
//        return systemOrder;
//    }
    public SystemOrder getSystemOrderForSession(HttpSession session, Long orderId) {
        SystemOrder systemOrder;
        //если в сессии нет текущего заказа(формируем новый заказ)
        if(orderId == 0) {
            ShoppingCart cart = cartService.getShoppingCartForSession(session);
            User user = (User)session.getAttribute("user");
            SystemDelivery systemDelivery = new SystemDelivery(user.getPhoneNumber(),
                    user.getDeliveryAddress(), BigDecimal.valueOf(100),
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
            BigDecimal totalCosts = cart.getTotalCost().add(systemDelivery.getDeliveryCost());
            systemOrder = new SystemOrder(user, cart.getCartItems(),
                    cart.getTotalCost(), totalCosts, systemDelivery);
        //если вызываем существующий заказ
        } else {
            //клонируем заказ в объект текущего заказа
            systemOrder = new SystemOrder(orderRepository.getOne(orderId));
        }

        //        return new SystemOrder(user, cart.getCartItems(),
        //                cart.getTotalCost(), totalCosts, systemDelivery);
        session.setAttribute("order", systemOrder);

        return systemOrder;
    }

    public void rollBackToCart(HttpSession session) {
        cartService.rollBackToCart(session);
    }

//    public void save(Order order) {
//        System.out.println(order);
////        orderRepository.save(order);
//    }
    @Transactional
    public boolean save(SystemOrder systemOrder) {
        //создаем черновик заказа
        Order order = createNewDraftOrUpdateOrder(systemOrder);
        //если это новый заказ
        if(systemOrder.getId() == null) {
            //сохраняем черновик заказа, чтобы получить orderId
            orderRepository.save(order);
        //если обновляется существующий заказ
        } else {
            //удаляем предыдущий список товаров заказа
            orderItemRepository.deleteOrderItemsByOrderId(order.getId());
        }
        //сохраняем в БД и записываем в заказ обновленные объекты элементов заказа
        order.setOrderItems(saveOrderItems(systemOrder.getOrderItems(), order));
        //создаем новый объект доставки
        Delivery delivery = createDelivery(systemOrder.getSystemDelivery(), order);
        //сохраняем объект доставка в БД
        deliveryService.save(delivery);
        //записываем в заказ обновленный объект доставки
        order.setDelivery(delivery);
        System.out.println(order);
        return isOrderSavedCorrectly(orderRepository.save(order), systemOrder);
    }

//    private Delivery saveDelivery(SystemDelivery systemDelivery, Order order) {
//        Delivery delivery;
//        if(systemDelivery.getId() == null) {
//            delivery = new Delivery();
//            delivery.setOrder(order);
//        } else {
//            delivery = deliveryService.findById(systemDelivery.getId());
//        }
//        delivery.setPhoneNumber(systemDelivery.getPhoneNumber());
//        delivery.setDeliveryAddress(systemDelivery.getDeliveryAddress());
//        delivery.setDeliveryCost(systemDelivery.getDeliveryCost());
//        delivery.setDeliveryExpectedAt(systemDelivery.getDeliveryExpectedAt());
//        delivery.setDeliveredAt(systemDelivery.getDeliveredAt());
//        deliveryService.save(delivery);
////        return deliveryService.findById(delivery.getId());
//        return delivery;
//    }
    private Delivery createDelivery(SystemDelivery systemDelivery, Order order) {
        Delivery delivery;
        if(systemDelivery.getId() == null) {
            delivery = new Delivery();
            delivery.setOrder(order);
        } else {
            delivery = deliveryService.findById(systemDelivery.getId());
        }
        delivery.setPhoneNumber(systemDelivery.getPhoneNumber());
        delivery.setDeliveryAddress(systemDelivery.getDeliveryAddress());
        delivery.setDeliveryCost(systemDelivery.getDeliveryCost());
        delivery.setDeliveryExpectedAt(systemDelivery.getDeliveryExpectedAt());
        delivery.setDeliveredAt(systemDelivery.getDeliveredAt());
        return delivery;
    }

    Order createNewDraftOrUpdateOrder(SystemOrder systemOrder) {
        Order order;
        if(systemOrder.getId() == null) {
            order = new Order();
            order.setOrderStatus(orderStatusRepository
                    .getOrderStatusByTitleEquals("Created"));
        } else {
            order = orderRepository.getOne(systemOrder.getId());
            order.setOrderStatus(systemOrder.getOrderStatus());
        }
        User user = userService.findByUserName(systemOrder.getUser().getUserName());
        order.setUser(user);
        order.setTotalItemsCosts(systemOrder.getTotalItemsCosts());
        order.setTotalCosts(systemOrder.getTotalCosts());
        return order;
    }

    //    public void delete(Order order) {
//        orderRepository.delete(order);
//    }
    @Transactional
    public void delete(Long orderId) {
        orderItemRepository.deleteOrderItemsByOrderId(orderId);
        deliveryService.deleteByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }

    public void cancelOrder(Long orderId) {
        //просто меняем статус на "Canceled" и оставляем заказ в списке
        Order order = findById(orderId);
        order.setOrderStatus(findOrderStatusByTitle("Canceled"));
//        save(order);
        System.out.println("********* canceled order *********");
        System.out.println(order);
    }

//    public void updateOrderDetails(Order formOrder) {
//        Order existedOrder = findById(formOrder.getId());
//        existedOrder.setOrderStatus(formOrder.getOrderStatus());
//        existedOrder.setOrderItems(formOrder.getOrderItems());
//        recalculate(existedOrder);
//        existedOrder.setDelivery(formOrder.getDelivery());
////        save(existedOrder);
//        System.out.println("********* updated order *********");
//        System.out.println(existedOrder);
//    }
    public Order recalculateOrderCosts(Order order, OrderItem orderItem) {
        updateOrderItemQuantity(order, orderItem);
        recalculate(order);
        System.out.println("********* recalculated order *********");
        System.out.println(order);
        return order;
    }

//    private boolean isOrderSavedCorrectly(Order order, ShoppingCart cart) {
//        List<OrderItem> cartOrderItems = cart.getCartItems();
//        List<OrderItem> orderItems = orderItemRepository.findAllOrderItemsByOrder(order);
//        //простая проверка сохраненного заказа
//        return orderItems.size() == cartOrderItems.size() &&
//                order.getTotalItemsCosts().equals(cart.getTotalCost());
//    }
    private boolean isOrderSavedCorrectly(Order order, SystemOrder systemOrder) {
        //простая проверка сохраненного заказа
        return order.getOrderItems().size() == systemOrder.getOrderItems().size() &&
                order.getTotalItemsCosts().equals(systemOrder.getTotalItemsCosts()) &&
                order.getTotalCosts().equals(systemOrder.getTotalCosts());
    }

    private void updateOrderItemQuantity(Order order, OrderItem orderItem) {
        for (OrderItem o : order.getOrderItems()) {
            if(o.getProduct().getId().equals(orderItem.getProduct().getId())) {
                o.setQuantity(orderItem.getQuantity());
            }
        }
    }

    private List<OrderItem> saveOrderItems(List<OrderItem> cartItems, Order order) {
        for (OrderItem i : cartItems) {
            i.setOrder(order);
            orderItemRepository.save(i);
        }
        return orderItemRepository.findAllOrderItemsByOrder(order);
    }

    public List<OrderStatus> findAllOrderStatuses() {
        return orderStatusRepository.findAll();
    }

    private void recalculate(Order order) {
        BigDecimal totalItemsCost = BigDecimal.ZERO;
        if(order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            for (OrderItem o : order.getOrderItems()) {
                recalculateItemCosts(o);
                totalItemsCost = totalItemsCost.add(o.getItemCosts());
            }
        }
        order.setTotalItemsCosts(totalItemsCost);
        order.setTotalCosts(totalItemsCost.add(order.getDelivery().getDeliveryCost()));
    }

    private void recalculateItemCosts(OrderItem orderItem) {
        orderItem.setItemCosts(BigDecimal.ZERO);
        orderItem.setItemCosts(orderItem.getItemPrice()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
    }

    public OrderItem findOrderItemByProdId(List<OrderItem> orderItems, Long prodId) {
        OrderItem orderItem = null;
        for (OrderItem o : orderItems) {
            if(o.getProduct().getId().equals(prodId)) {
                orderItem = o;
            }
        }
        return orderItem;
    }

}

//Integer.valueOf(Year.now().toString()), Month.JUNE, MonthDay.of(Month.JUNE, 3))));
