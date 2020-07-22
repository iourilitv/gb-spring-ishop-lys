package ru.geekbrains.spring.ishop.utils.websocket;

public class Response {
    private final String prodId;
    private final String quantity;
    private final String cartItemsQuantity;
    private String cartItemCost;
    private String totalCost;

    public Response(String prodId, String quantity, String cartItemsQuantity) {
        this.prodId = prodId;
        this.quantity = quantity;
        this.cartItemsQuantity = cartItemsQuantity;
    }

    public Response(String prodId, String quantity, String cartItemsQuantity,
                    String cartItemCost, String totalCost) {
        this.prodId = prodId;
        this.quantity = quantity;
        this.cartItemsQuantity = cartItemsQuantity;
        this.cartItemCost = cartItemCost;
        this.totalCost = totalCost;
    }

    public String getProdId() {
        return prodId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCartItemsQuantity() {
        return cartItemsQuantity;
    }

    public String getCartItemCost() {
        return cartItemCost;
    }

    public String getTotalCost() {
        return totalCost;
    }
}
