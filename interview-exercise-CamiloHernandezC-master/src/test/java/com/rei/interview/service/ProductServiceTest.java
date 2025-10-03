package com.rei.interview.service;

import com.rei.interview.model.Product;
import com.rei.interview.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setProductId("123456");
        testProduct.setBrand("REI");
        testProduct.setDescription("Test Product");
        testProduct.setPrice(new BigDecimal("29.99"));
    }

    @Test
    void shouldReturnProductWhenProductExists() {
        when(productRepository.getProduct("123456")).thenReturn(testProduct);

        Product result = productService.getProduct("123456");

        assertNotNull(result);
        assertEquals("123456", result.getProductId());
    }

    @Test
    void shouldReturnNullWhenProductDoesNotExist() {
        when(productRepository.getProduct("999999")).thenReturn(null);

        Product result = productService.getProduct("999999");

        assertNull(result);
    }

    @Test
    void shouldReturnProductsByBrand() {
        List<Product> products = Arrays.asList(testProduct);
        when(productRepository.getProductsByBrand("REI")).thenReturn(products);

        List<Product> result = productService.getProductsByBrand("REI");

        assertEquals(1, result.size());
        assertEquals("REI", result.get(0).getBrand());
    }

    @Test
    void shouldAddNewProduct() {
        doNothing().when(productRepository).addProduct(testProduct);

        Product result = productService.addProduct(testProduct);

        assertNotNull(result);
        assertEquals("123456", result.getProductId());
        verify(productRepository).addProduct(testProduct);
    }

    @Test
    void shouldReturnAllProducts() {
        List<Product> products = Arrays.asList(testProduct);
        when(productRepository.getProductsByBrand(anyString())).thenReturn(products);

        List<Product> result = productService.getProductsByBrand("REI");

        assertEquals(1, result.size());
        verify(productRepository).getProductsByBrand("REI");
    }
}