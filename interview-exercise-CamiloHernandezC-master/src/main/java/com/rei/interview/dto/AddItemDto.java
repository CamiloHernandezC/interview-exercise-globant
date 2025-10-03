package com.rei.interview.dto;

import com.rei.interview.model.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddItemDto {

    @NotBlank(message = "Product ID cannot be blank")
    private String productId;
    
    @Positive(message = "Quantity must be positive")
    private int quantity;
    
    @NotNull(message = "Location cannot be null")
    private Location location;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}