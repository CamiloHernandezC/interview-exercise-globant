package com.rei.interview.repository;

import com.rei.interview.model.Product;
import com.rei.interview.util.Cache;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    private Map<String, Product> products = new Cache<>();

    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
    }

    public Product getProduct(String productId) {
        return products.get(productId);
    }

    public Collection<Product> getAll() {
        return products.values();
    }

    public List<Product> getProductsByBrand(String brand) {
        if (brand == null) {
            return List.of();
        }
        return products.values().stream()
                .filter(product -> brand.equalsIgnoreCase(product.getBrand()))
                .collect(Collectors.toList());
    }

    public boolean productExists(String productId) {
        return products.containsKey(productId);
    }
}