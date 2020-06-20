package ru.geekbrains.spring.ishop.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "item_costs")
    private BigDecimal itemCosts;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public String toString() {
        long orderId = order == null ? 0 : order.getId();
        return "OrderItem{" +
                "id=" + id +
                ", product=" + product +
                ", itemPrice=" + itemPrice +
                ", quantity=" + quantity +
                ", itemCosts=" + itemCosts +
                ", orderId=" + orderId +
                '}';
    }
}
