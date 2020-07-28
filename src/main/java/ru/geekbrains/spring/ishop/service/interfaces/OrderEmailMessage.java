package ru.geekbrains.spring.ishop.service.interfaces;

import ru.geekbrains.spring.ishop.entity.Order;

public class OrderEmailMessage extends AbstractMailMessage {
    public Order getOrder() {
        return (Order) super.origin;
    }

}
