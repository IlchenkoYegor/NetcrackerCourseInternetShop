package com.edu.NetcrackerLAB3.IlchenkoYegor.model;

import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Order;

import java.sql.Date;
import java.util.List;


public class OrderInfo {
    private int accountId;
    private int id;
    private int orderNum;
    private double amount;
    private Date orderDate;


    private List<OrderDetailInfo> details;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderInfo() {

    }

    public OrderInfo(Order order) {
        this.id = order.getOrderId();
        this.orderNum = order.getOrderNum();
        this.amount = order.getAmount();
        this.accountId = order.getAccountId();
        this.orderDate = order.getOrderDate();
    }

    public int getId() {
        return id;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<OrderDetailInfo> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailInfo> details) {
        this.details = details;
    }

}

