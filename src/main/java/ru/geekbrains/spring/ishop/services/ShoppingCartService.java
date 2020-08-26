package ru.geekbrains.spring.ishop.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.OrderItem;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.function.Supplier;

@Component
@Data
public class ShoppingCartService {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void addToCart(HttpSession session, Long prod_id) throws Throwable {
        ShoppingCart cart = getShoppingCartForSession(session);
        Product product = productService.findById(prod_id);
        if(cart.getCartItems().isEmpty() || !cartContains(cart, product.getId())) {
            cart.getCartItems().add(newCartItem(product, 1));
        } else {
            OrderItem cartItem = findCartItemByProductId(cart, product.getId());
            increaseItemsQuantity(cartItem);
        }
        recalculate(cart);
    }

    public void updateItemQuantity(HttpSession session, Long prod_id, int newQuantity) throws Throwable {
        ShoppingCart cart = getShoppingCartForSession(session);
        cart.getCartItems().stream().filter(o ->
                o.getProduct().getId().equals(prod_id)).findFirst()
                .orElseThrow((Supplier<Throwable>) () -> null)
                .setQuantity(newQuantity);
        recalculate(cart);
    }

    public void removeItemFromCartById(HttpSession session, Long prod_id) throws Throwable {
        ShoppingCart cart = getShoppingCartForSession(session);
        OrderItem item = cart.getCartItems().stream().filter(o ->
                o.getProduct().getId().equals(prod_id)).findAny()
                .orElseThrow((Supplier<Throwable>) () -> null);
        cart.getCartItems().remove(item);
        recalculate(cart);
    }

    public void clearCart(HttpSession session) {
        ShoppingCart cart = getShoppingCartForSession(session);
        cart.getCartItems().clear();
    }

    public ShoppingCart getShoppingCartForSession(HttpSession session) {
        ShoppingCart cart;
        if (session.getAttribute("cart") == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        } else {
            cart = (ShoppingCart) session.getAttribute("cart");
        }
        return cart;
    }

    private OrderItem findCartItemByProductId(ShoppingCart cart, Long prod_id) throws Throwable {
        return cart.getCartItems().stream().filter(o ->
                o.getProduct().getId().equals(prod_id)).findAny()
                .orElseThrow((Supplier<Throwable>) () -> null);
    }

    private boolean cartContains(ShoppingCart cart, Long prod_id){
        return cart.getCartItems().stream().anyMatch(o ->
                o.getProduct().getId().equals(prod_id));
    }

    private OrderItem newCartItem(Product product, int quantity){
        OrderItem cartItem = new OrderItem();
        cartItem.setId(1L);
        cartItem.setProduct(product);
        cartItem.setItemPrice(product.getPrice());
        cartItem.setQuantity(quantity);
        cartItem.setItemCosts(product.getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        return cartItem;
    }

    private void increaseItemsQuantity(OrderItem cartItem) {
        cartItem.setQuantity(cartItem.getQuantity() + 1);
    }

    private void recalculate(ShoppingCart cart) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItem o : cart.getCartItems()) {
            recalculateItemCosts(o);
            totalCost = totalCost.add(o.getItemCosts());
        }
        cart.setTotalCost(totalCost);
    }

    private void recalculateItemCosts(OrderItem orderItem) {
        orderItem.setItemCosts(BigDecimal.ZERO);
        orderItem.setItemCosts(orderItem.getItemPrice()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
    }

}
