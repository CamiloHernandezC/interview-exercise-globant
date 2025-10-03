package com.rei.interview.controller;

import com.rei.interview.dto.CreateProductDto;
import com.rei.interview.model.Product;
import com.rei.interview.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private Product testProduct;
    private Product testProduct2;
    private CreateProductDto createProductDto;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setProductId("123456");
        testProduct.setBrand("REI");
        testProduct.setDescription("Test Product");
        testProduct.setPrice(new BigDecimal("29.99"));

        testProduct2 = new Product();
        testProduct2.setProductId("789012");
        testProduct2.setBrand("REI");
        testProduct2.setDescription("Test Product 2");
        testProduct2.setPrice(new BigDecimal("49.99"));

        createProductDto = new CreateProductDto();
        createProductDto.setProductId("NEW123");
        createProductDto.setBrand("NewBrand");
        createProductDto.setDescription("New Product Description");
        createProductDto.setPrice(new BigDecimal("39.99"));
    }

    @Test
    void shouldReturn200AndProductWhenProductExists() throws Exception {
        when(productService.getProduct("123456")).thenReturn(testProduct);

        mockMvc.perform(get("/products/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("123456"));
    }

    @Test
    void shouldReturn404WhenProductDoesNotExist() throws Exception {
        when(productService.getProduct("999999")).thenReturn(null);

        mockMvc.perform(get("/products/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200AndProductsWhenBrandHasProducts() throws Exception {
        List<Product> products = Arrays.asList(testProduct, testProduct2);
        when(productService.getProductsByBrand("REI")).thenReturn(products);

        mockMvc.perform(get("/products/brand/REI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturn201AndProductWhenProductCreatedSuccessfully() throws Exception {
        Product newProduct = new Product();
        newProduct.setProductId("NEW123");
        newProduct.setBrand("NewBrand");
        when(productService.addProduct(any(Product.class))).thenReturn(newProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value("NEW123"));
    }

    @Test
    void shouldReturn400WhenProductDataIsInvalid() throws Exception {
        createProductDto.setProductId(null);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200AndAllProducts() throws Exception {
        List<Product> products = Arrays.asList(testProduct, testProduct2);
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}