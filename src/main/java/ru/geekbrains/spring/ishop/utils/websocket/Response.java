package ru.geekbrains.spring.ishop.utils.websocket;

public class Response {
    private String prodId;
    private String quantity;
    private int cartItemsQuantity;

    public Response() {
    }

    public Response(String prodId, String quantity, int cartItemsQuantity) {
        this.prodId = prodId;
        this.quantity = quantity;
        this.cartItemsQuantity = cartItemsQuantity;
    }

    public String getProdId() {
        return prodId;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getCartItemsQuantity() {
        return cartItemsQuantity;
    }
}
