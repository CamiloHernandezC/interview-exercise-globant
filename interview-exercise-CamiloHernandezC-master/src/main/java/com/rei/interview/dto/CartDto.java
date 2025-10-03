package com.rei.interview.dto;

import java.util.List;

public class CartDto {

    private String cartId;
    private List<CartItemDto> items;


    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
}