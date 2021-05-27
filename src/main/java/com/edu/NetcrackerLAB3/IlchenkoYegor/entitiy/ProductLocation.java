package com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy;

public class ProductLocation {
    int productId;
    String city;
    String country;
    String address;

    public ProductLocation() {
    }

    public ProductLocation(int productId, String city, String country, String address) {
        this.productId = productId;
        this.city = city;
        this.country = country;
        this.address = address;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

