package com.rei.interview.controller;

import com.rei.interview.dto.CreateProductDto;
import com.rei.interview.dto.ProductDto;
import com.rei.interview.model.Product;
import com.rei.interview.service.ProductService;
import com.rei.interview.util.DtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        return handleRequest(() -> {
            Product product = productService.getProduct(productId);
            return product != null ? ResponseEntity.ok(DtoMapper.toDto(product)) 
                                   : ResponseEntity.notFound().build();
        });
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ProductDto>> getProductsByBrand(@PathVariable String brand) {
        return handleRequest(() -> {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products == null || products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(DtoMapper.toDtoList(products));
        });
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        return handleRequest(() -> {
            Product product = DtoMapper.toModel(createProductDto);
            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toDto(savedProduct));
        }, IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return handleRequest(() -> {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(DtoMapper.toDtoList(products));
        });
    }

    // Utility methods for cleaner code
    private <T> ResponseEntity<T> handleRequest(Supplier<ResponseEntity<T>> operation) {
        return handleRequest(operation, Exception.class, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private <T> ResponseEntity<T> handleRequest(Supplier<ResponseEntity<T>> operation, 
                                              Class<? extends Exception> specificException, 
                                              HttpStatus specificStatus) {
        try {
            return operation.get();
        } catch (Exception e) {
            if (specificException.isInstance(e)) {
                return ResponseEntity.status(specificStatus).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Validation failed: " + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
}