package com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy;

import java.io.Serializable;

public class Account {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getCustomerAdress() {
        return customerAdress;
    }

    public void setCustomerAdress(String customerAdress) {
        this.customerAdress = customerAdress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public Account() {
    }

    public Account(String userName, String password, boolean active, String userRole, String customerAdress, String customerEmail, String customerName, String customerPhone, int userId) {
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.userRole = userRole;
        this.customerAdress = customerAdress;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        UserId = userId;
    }

    public Account(String userName, String password, boolean active, String userRole, String customerAdress, String customerEmail, String customerName, String customerPhone) {
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.userRole = userRole;
        this.customerAdress = customerAdress;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    public static final String ROLE_UNREGISTRED = "UNREGISTRED";

    private String userName;
    private String password;
    private boolean active;
    private String userRole;
    private String customerAdress;
    private String customerEmail;
    private String customerName;
    private String customerPhone;
    private int UserId;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}