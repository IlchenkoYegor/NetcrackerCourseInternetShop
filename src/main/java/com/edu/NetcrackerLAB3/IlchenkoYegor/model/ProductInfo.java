package com.edu.NetcrackerLAB3.IlchenkoYegor.model;

import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.ProductLocation;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Products;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ProductInfo {
    private String code;
    private String name;
    private float price;
    private String locationCity;
    private String locationCountry;
    private String locationAddress;
    private int productId;
    private int recommended;

    public int getRecommended() {
        return recommended;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", locationCity='" + locationCity + '\'' +
                ", locationCountry='" + locationCountry + '\'' +
                ", locationAddress='" + locationAddress + '\'' +
                ", productId=" + productId +
                ", recommended=" + recommended +
                ", newProduct=" + newProduct +
                ", fileData=" + fileData +
                '}';
    }

    public void setRecommended(int recommended) {
        this.recommended = recommended;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationCountry() {
        return locationCountry;
    }

    public void setLocationCountry(String locationCountry) {
        this.locationCountry = locationCountry;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    private boolean newProduct=false;

    // Upload file.
    private CommonsMultipartFile fileData;

    public ProductInfo() {
    }

    public ProductInfo(Products product, ProductLocation productLocation) {
        this.productId = product.getProductId();
        this.recommended = product.getRecomended();
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.locationAddress = productLocation.getAddress();
        this.locationCity = productLocation.getCity();
        this.locationCountry = productLocation.getCountry();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public CommonsMultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }

    public boolean isNewProduct() {
        return newProduct;
    }

    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }

}