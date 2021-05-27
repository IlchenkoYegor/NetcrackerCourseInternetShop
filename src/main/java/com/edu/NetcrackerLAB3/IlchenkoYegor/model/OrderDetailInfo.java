package com.edu.NetcrackerLAB3.IlchenkoYegor.model;

import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.OrderDetails;

public class OrderDetailInfo {
    private int id;
    private String Name;
    private int productId;
    private String productName;


    private int quanity;
    private double price;
    private double amount;

    public OrderDetailInfo() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public OrderDetailInfo(OrderDetails orderDetails, String productName){
        this.id = orderDetails.getOrderId();
        this.Name = orderDetails.getName();
        this.price = orderDetails.getPrice();
        this.amount = orderDetails.getAmount();
        this.productId = orderDetails.getProductId();
        this.productName = productName;
        this.quanity = orderDetails.getQuantity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}