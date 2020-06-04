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
import ru.geekbrains.spring.ishop.utils.OrderFilter;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;
import ru.geekbrains.spring.ishop.utils.UtilFilter;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Service
public class OrderService {
    private DeliveryService deliveryService;
    private AddressRepository addressRepository;
    private OrderStatusRepository orderStatusRepository;
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;
    private UtilFilter utilFilter;

    @Autowired
    public OrderService(DeliveryService deliveryService, AddressRepository addressRepository,
                        OrderStatusRepository orderStatusRepository, OrderItemRepository orderItemRepository,
                        OrderRepository orderRepository, UtilFilter utilFilter) {
        this.deliveryService = deliveryService;
        this.addressRepository = addressRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.utilFilter = utilFilter;
    }

    public Page<Order> findAll(OrderFilter filter, String property) {
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(utilFilter.getPageIndex(),
                utilFilter.getLimit(), utilFilter.getDirection(), property);
        return orderRepository.findAll(filter.getSpec(), pageRequest);
    }

    public Order findById(Long id) {
        return orderRepository.getOne(id);
    }

    public OrderStatus findOrderStatusByTitle(String title) {
        return orderStatusRepository.getOrderStatusByTitleEquals(title);
    }

    //TODO с ним не сохраняет сущности в бд при даже если нет ошибки отображения страницы
    // java.lang.StackOverflowError
    // спринг создает сущности, дает им id и если ошибки нет,
    // то сохраняет их в бд с этим id. Но при ошибке - уничтожает созданные сущности и
    // эти id уже не используются!
//    @Transactional
    public boolean create(ShoppingCart cart, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Order order = new Order();
        order.setOrderStatus(orderStatusRepository
                .getOrderStatusByTitleEquals("Created"));
        order.setUser(user);
        order.setTotalItemsCosts(cart.getTotalCost());

        Delivery delivery = deliveryService.getDeliveryForSession(session);
        delivery.setPhoneNumber(user.getPhoneNumber());

        //TODO temporarily - добавить выбор адреса
//        delivery.setDeliveryAddress(user.getDeliveryAddress());
        delivery.setDeliveryAddress(addressRepository.getOne(1L));
        //TODO добавить сервис расчета стоимости доставки
        delivery.setDeliveryCost(new BigDecimal(100));
        //TODO добавить ввод значений в интерфейс
        delivery.setDeliveryExpectedAt(LocalDateTime.of(
                LocalDate.of(2020, 6, 3),
                LocalTime.of(14, 20)));

        order.setTotalCosts(order.getTotalItemsCosts().add(delivery.getDeliveryCost()));
        //Сохраняем сначала заготовку заказа, чтобы получить id
        orderRepository.save(order);
        //сохраняем объект доставки уже привязанный к заказу
        delivery.setOrder(order);
        deliveryService.save(delivery);
        //дополняем заказ объектом доставки
        order.setDelivery(delivery);
        //сохраняем объекты элементов заказа и добавляем его в заказ
        order.setOrderItems(saveOrderItems(cart.getCartItems(), order));
        //окончательно записываем заказ
        orderRepository.save(order);
        if(isOrderSavedCorrectly(order, cart)) {
            session.setAttribute("order", order);
            return true;
        }
        return false;
    }

    private boolean isOrderSavedCorrectly(Order order, ShoppingCart cart) {
        List<OrderItem> cartOrderItems = cart.getCartItems();
        List<OrderItem> orderItems = orderItemRepository.findAllOrderItemsByOrder(order);
        //простая проверка сохраненного заказа
        return orderItems.size() == cartOrderItems.size() &&
                order.getTotalItemsCosts().equals(cart.getTotalCost());
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    private List<OrderItem> saveOrderItems(List<OrderItem> cartItems, Order order) {
        for (OrderItem i : cartItems) {
            i.setOrder(order);
            orderItemRepository.save(i);
        }
        return orderItemRepository.findAllOrderItemsByOrder(order);
    }

}

//Integer.valueOf(Year.now().toString()), Month.JUNE, MonthDay.of(Month.JUNE, 3))));
