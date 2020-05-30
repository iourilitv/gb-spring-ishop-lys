package ru.geekbrains.spring.ishop.utils;

import lombok.Data;
import ru.geekbrains.spring.ishop.entity.OrderItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCart {
    private List<OrderItem> cartItems;
    private BigDecimal totalCost;

    public ShoppingCart() {
        cartItems = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
    }

//    public void addItem(Product product) throws Throwable {
//        if(cartItems.isEmpty() || !cartContains(product.getId())) {
//            cartItems.add(newCartItem(product, 1));
//        } else {
//            OrderItem cartItem = findCartItemByProductId(product.getId());
//            increaseItemsQuantity(cartItem);
//        }
//    }



//    public OrderItem newCartItem(Product product, int quantity){
//        OrderItem cartItem = new OrderItem();
//        cartItem.setId(1L);
//        cartItem.setProduct(product);
//        cartItem.setItemPrice(product.getPrice());
//        cartItem.setQuantity(quantity);
//        cartItem.setItemCosts(product.getPrice()
//                .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
//        return cartItem;
//    }

//    public boolean cartContains(Product product){
//        return cartItems.stream().anyMatch(o ->
//                o.getProduct().equals(product));
//    }
//    public boolean cartContains(Long prod_id){
//        return cartItems.stream().anyMatch(o ->
//                o.getProduct().getId().equals(prod_id));
//    }

//    public OrderItem findCartItemByProduct(Product product) throws Throwable {
//        return cartItems.stream().filter(o ->
//                o.getProduct().getId().equals(product.getId())).findAny()
//                .orElseThrow((Supplier<Throwable>) () -> null);
//    }
//    public OrderItem findCartItemByProductId(Long prod_id) throws Throwable {
//        return cartItems.stream().filter(o ->
//                o.getProduct().getId().equals(prod_id)).findAny()
//                .orElseThrow((Supplier<Throwable>) () -> null);
//    }

//    private void increaseItemsQuantity(OrderItem cartItem) {
//        cartItem.setQuantity(cartItem.getQuantity() + 1);
//    }

}
