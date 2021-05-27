package com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy;

import java.sql.Date;

public class Order {
    private int orderId;
    private int accountId;
    private float amount;
    private Date orderDate;
    private int orderNum;

    public Order(int orderId, int accountId, float amount, Date orderDate, int orderNum) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.amount = amount;
        this.orderDate = orderDate;
        this.orderNum = orderNum;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", orderDate=" + orderDate +
                ", orderNum=" + orderNum +
                '}';
    }
}
