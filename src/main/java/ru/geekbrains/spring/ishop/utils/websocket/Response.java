package ru.geekbrains.spring.ishop.utils.websocket;

public class Response {
//    private String content;
//
//    public Response(String content) {
//        this.content = content;
//    }
//
//    public String getContent() {
//        return content;
//    }

//    private Long prodId;
//    private int quantity;
//
//    public Response() {
//    }
//
//    public Response(Long prodId, int quantity) {
//        this.prodId = prodId;
//        this.quantity = quantity;
//    }
//
//    public Long getProdId() {
//        return prodId;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
    private String prodId;
    private String quantity;
    private int cartItemsQuantity;

    public Response() {
    }

//    public Response(String prodId, String quantity) {
//        this.prodId = prodId;
//        this.quantity = quantity;
//    }
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
