package com.rei.interview.repository;

import com.rei.interview.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    private ProductRepository productRepository;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        testProduct = new Product();
        testProduct.setProductId("123456");
        testProduct.setBrand("REI");
        testProduct.setPrice(new BigDecimal("29.99"));
    }

    @Test
    void shouldAddProduct() {
        productRepository.addProduct(testProduct);
        
        Product result = productRepository.getProduct("123456");
        assertNotNull(result);
        assertEquals("123456", result.getProductId());
    }

    @Test
    void shouldGetProduct() {
        productRepository.addProduct(testProduct);
        
        Product result = productRepository.getProduct("123456");
        
        assertNotNull(result);
        assertEquals("123456", result.getProductId());
    }

    @Test
    void shouldGetProductsByBrand() {
        productRepository.addProduct(testProduct);
        
        List<Product> result = productRepository.getProductsByBrand("REI");
        
        assertEquals(1, result.size());
        assertEquals("REI", result.get(0).getBrand());
    }
}