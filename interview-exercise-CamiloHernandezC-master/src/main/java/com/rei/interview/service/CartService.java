package com.rei.interview.service;

import java.math.BigDecimal;

import com.rei.interview.model.Cart;
import com.rei.interview.model.Location;
import com.rei.interview.model.Product;
import com.rei.interview.repository.CartRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final CartRepository cartRepository;

    public CartService(ProductService productService, InventoryService inventoryService, CartRepository cartRepository) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.cartRepository = cartRepository;
    }

    public Cart addToCart(String cartId, Product product, int quantity, Location location) throws Exception {
        validateProduct(product);
        
        if (!hasAvailableInventory(product, quantity, location)) {
            throw new IllegalArgumentException("No inventory available for this product");
        }
        
        Cart cart = getOrCreateCart(cartId);
        cart.getProducts().merge(product, quantity, Integer::sum);
        
        return cart;
    }
    
    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        
        if (!isValidProductData(product)) {
            throw new IllegalArgumentException("Invalid Product");
        }
    }
    
    private boolean isValidProductData(Product product) {
        return StringUtils.isNumeric(product.getProductId()) 
            && product.getProductId().length() == 6
            && !StringUtils.isBlank(product.getBrand())
            && !StringUtils.isBlank(product.getDescription())
            && product.getPrice() != null
            && product.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }
    
    private boolean hasAvailableInventory(Product product, int quantity, Location location) {
        return inventoryService.hasInventoryOnline(product, quantity) 
            || inventoryService.hasInventoryInNearbyStores(product, quantity, location);
    }
    
    private Cart getOrCreateCart(String cartId) {
        if (StringUtils.isBlank(cartId)) {
            return createNewCart();
        }
        
        Cart cart = cartRepository.getCart(cartId);
        return cart != null ? cart : createNewCart();
    }
    
    private Cart createNewCart() {
        Cart cart = new Cart();
        cartRepository.addCart(cart);
        return cart;
    }
}