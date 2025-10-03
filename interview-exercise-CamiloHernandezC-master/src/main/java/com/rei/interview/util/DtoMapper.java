package com.rei.interview.util;

import com.rei.interview.dto.CartDto;
import com.rei.interview.dto.CartItemDto;
import com.rei.interview.dto.CreateProductDto;
import com.rei.interview.dto.ProductDto;
import com.rei.interview.model.Cart;
import com.rei.interview.model.Product;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for mapping between DTOs and domain models
 * Centralizes transformation logic to reduce code duplication
 */
public final class DtoMapper {
    
    private DtoMapper() {
        // Utility class - prevent instantiation
    }
    
    // Product transformations
    public static ProductDto toDto(Product product) {
        return new ProductDto(
            product.getProductId(),
            product.getBrand(),
            product.getDescription(),
            product.getPrice()
        );
    }
    
    public static List<ProductDto> toDtoList(List<Product> products) {
        return products.stream().map(DtoMapper::toDto).collect(Collectors.toList());
    }
    
    public static Product toModel(CreateProductDto dto) {
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setBrand(dto.getBrand());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return product;
    }
    
    // Cart transformations
    public static CartDto toDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setCartId(cart.getCartId().toString());
        
        List<CartItemDto> items = cart.getProducts().entrySet().stream()
            .map(entry -> new CartItemDto(entry.getKey().getProductId(), entry.getValue()))
            .collect(Collectors.toList());
        
        dto.setItems(items);
        return dto;
    }
}