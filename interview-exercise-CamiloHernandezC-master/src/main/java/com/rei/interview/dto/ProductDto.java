package com.rei.interview.dto;

import java.math.BigDecimal;

public class ProductDto {

    private String productId;
    private String brand;
    private String description;
    private BigDecimal price;

    public ProductDto() {}

    public ProductDto(String productId, String brand, String description, BigDecimal price) {
        this.productId = productId;
        this.brand = brand;
        this.description = description;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.productId;
    }
}