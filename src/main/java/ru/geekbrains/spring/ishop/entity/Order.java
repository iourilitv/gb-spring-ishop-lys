package ru.geekbrains.spring.ishop.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Column(name = "total_items_costs")
    private BigDecimal totalItemsCosts;

    @Column(name = "total_costs")
    private BigDecimal totalCosts;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderStatus=" + orderStatus +
//                ", user=" + user +
                ", userId=" + user.getId() +
                ", orderItems=" + orderItems +
                ", totalItemsCosts=" + totalItemsCosts +
                ", totalCosts=" + totalCosts +
                ", delivery=" + delivery +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
