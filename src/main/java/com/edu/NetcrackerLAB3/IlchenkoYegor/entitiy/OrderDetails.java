package com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy;

public class OrderDetails {
    private String name;
    private float amount;
    private float price;
    private int quantity;
    private int orderId;
    private int productId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public OrderDetails() {
    }

    public OrderDetails(String name, float amount, float price, int quantity, int orderId, int productId) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.productId = productId;
    }
}
