package com.rei.interview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class CreateProductDto {

    @NotBlank(message = "Product ID cannot be blank")
    private String productId;
    
    @NotBlank(message = "Brand cannot be blank")
    private String brand;
    
    @NotBlank(message = "Description cannot be blank")
    private String description;
    
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    public CreateProductDto() {}

    public CreateProductDto(String productId, String brand, String description, BigDecimal price) {
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
}