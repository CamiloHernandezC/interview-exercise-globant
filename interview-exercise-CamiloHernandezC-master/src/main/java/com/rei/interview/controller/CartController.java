package com.rei.interview.controller;

import com.rei.interview.dto.AddItemDto;
import com.rei.interview.dto.CartDto;
import com.rei.interview.dto.CartItemDto;
import com.rei.interview.dto.ProductDto;
import com.rei.interview.util.DtoMapper;
import com.rei.interview.model.Cart;
import com.rei.interview.model.Product;
import com.rei.interview.service.CartService;
import com.rei.interview.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Cart service is running");
    }

    @PostMapping("/{cartId}")
    public ResponseEntity<CartDto> addToCart(@PathVariable("cartId") String cartId,
                                           @Valid @RequestBody AddItemDto addItem) {
        try {
            Product product = productService.getProduct(addItem.getProductId());
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            
            Cart cart = cartService.addToCart(cartId, product, addItem.getQuantity(), addItem.getLocation());
            return ResponseEntity.ok(DtoMapper.toDto(cart));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }


}