package com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy;

import java.sql.Date;

public class Products {
    private int productId;
    private int recomended;
    private String code;
    private byte[] image;
    private float price;
    private Date createDate;
    private String name;
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRecomended() {
        return recomended;
    }

    public void setRecomended(int recomended) {
        this.recomended = recomended;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Products() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Products(int productId, int recomended, String code, byte[] image, float price, Date createDate, String name) {
        this.productId = productId;
        this.recomended = recomended;
        this.code = code;
        this.image = image;
        this.price = price;
        this.createDate = createDate;
        this.name = name;
    }
}

