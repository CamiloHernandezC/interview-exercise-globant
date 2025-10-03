package com.rei.interview.service;

import com.rei.interview.model.Product;
import com.rei.interview.repository.ProductRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean isValidProduct(Product product) {
        return true;
    }

    public Product getProduct(String productId) {
        return productRepository.getProduct(productId);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.getAll());
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.getProductsByBrand(brand);
    }

    public Product addProduct(Product product) {
        if (product == null || product.getProductId() == null || product.getProductId().trim().isEmpty()) {
            throw new IllegalArgumentException("Product and productId cannot be null or empty");
        }
        
        if (productRepository.productExists(product.getProductId())) {
            throw new IllegalArgumentException("Product with ID " + product.getProductId() + " already exists");
        }
        
        productRepository.addProduct(product);
        return product;
    }

    /**
     * Populates the product repository with data from products.csv
     *
     * @throws IOException
     */
    @PostConstruct
    public void populateProducts() throws IOException {
        try(Reader in = new InputStreamReader(getClass().getResourceAsStream("/products.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("productId", "brand", "description", "price")
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                Product product = new Product();
                product.setProductId(record.get("productId"));
                product.setBrand(record.get("brand"));
                product.setDescription(record.get("description"));
                product.setPrice(new BigDecimal(record.get("price")));
                logger.info(product.toString());
                productRepository.addProduct(product);
            }
        }

        logger.info("Products loaded into product repository");
    }
}