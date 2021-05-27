package com.edu.NetcrackerLAB3.IlchenkoYegor.REST.Entity;

public class ProductStatistic {
    private String name;
    private double price;

    public ProductStatistic(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
