package ru.geekbrains.spring.ishop.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.OrderItem;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
@Data
public class ShoppingCartService {
    private ShoppingCart cart;
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Transactional
    public int addToCart(ShoppingCart cart, Long prod_id) throws Throwable {
        Product product = productService.findById(prod_id);
        OrderItem cartItem;
        //если корзина пуста и такого товара нет в корзине
        if(cart.getCartItems().isEmpty() || !cartContains(cart, product.getId())) {
            //создаем и добавляем новый элемент в корзину
            cartItem = newCartItem(product, 1);
            cart.getCartItems().add(cartItem);
            //в противном случае
        } else {
            //находим элемент и увеличиваем его количество на 1
            cartItem = findCartItemByProductId(cart, product.getId());
            increaseCartItemQuantity(cartItem);
        }
        //пересчитываем корзину
        recalculate(cart);
        //возвращаем новое количество элемента в корзине
        return cartItem.getQuantity();
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
        cart = getShoppingCartForSession(session);
        OrderItem item = cart.getCartItems().stream().filter(o ->
                o.getProduct().getId().equals(prod_id)).findAny()
                .orElseThrow((Supplier<Throwable>) () -> null);
        cart.getCartItems().remove(item);
        recalculate(cart);
    }

    public void clearCart(HttpSession session) {
        cart = getShoppingCartForSession(session);
        cart.getCartItems().clear();
    }

    public ShoppingCart getShoppingCartForSession(HttpSession session) {
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
                .orElse(null);
    }

    public int getQuantityOfCartItemByProdId(ShoppingCart cart, Long prod_id) throws Throwable {
        OrderItem orderItem = findCartItemByProductId(cart, prod_id);
        if(orderItem == null) {
            return 0;
        }
        return orderItem.getQuantity();
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

    public ShoppingCart getCart() {
        return cart;
    }

    public Map<String, Integer> getCartItemsQuantities(ShoppingCart cart) {
        Map<String, Integer> quantities = new HashMap<>();
        if(cart != null && !cart.getCartItems().isEmpty()) {
            for (OrderItem i : cart.getCartItems()) {
                quantities.put(
                        String.valueOf(i.getProduct().getId()),
                        i.getQuantity());
            }
        }
        return quantities;
    }

    public int getCartItemsQuantity(ShoppingCart cart) {
        int cartItemsQuantity = 0;
        if(cart != null && !cart.getCartItems().isEmpty()) {
            for (OrderItem i : cart.getCartItems()) {
                cartItemsQuantity += i.getQuantity();
            }
        }
        return cartItemsQuantity;
    }
}
