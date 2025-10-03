package com.rei.interview.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rei.interview.model.Location;
import com.rei.interview.model.Product;
import com.rei.interview.model.Store;

@Service
public class InventoryService {
    
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    private static final int MINIMUM_STORE_THRESHOLD = 3; // Minimum inventory for stores
    
    private final LocationService locationService;
    
    // Thread-safe map to store inventory: Key = "productId:location", Value = quantity
    private final Map<String, Integer> inventoryData = new ConcurrentHashMap<>();

    public InventoryService(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostConstruct
    public void readInInventory() {
        loadInventoryData();
    }
    
    @Scheduled(fixedRate = 300000) // Reload every 5 minutes (300,000 ms)
    public void reloadInventoryData() {
        logger.info("Reloading inventory data...");
        loadInventoryData();
    }
    
    private void loadInventoryData() {
        try (Reader in = new InputStreamReader(getClass().getResourceAsStream("/product_inventory.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("productId", "location", "quantity")
                    .withFirstRecordAsHeader()
                    .parse(in);

            // Clear existing data before loading new data
            inventoryData.clear();
            
            for (CSVRecord record : records) {
                String productId = record.get("productId");
                String location = record.get("location");
                int quantity = Integer.valueOf(record.get("quantity"));
                
                String key = productId + ":" + location;
                inventoryData.put(key, quantity);
                
                logger.debug("Loaded inventory: {} at {} with quantity {}", productId, location, quantity);
            }
            
            logger.info("Successfully loaded {} inventory records", inventoryData.size());
        } catch (IOException e) {
            logger.error("Error loading inventory data", e);
        }
    }

    public boolean hasInventoryOnline(Product product, int quantity) {
        if (!isValidRequest(product, quantity)) return false;
        
        int available = getAvailableQuantity(product.getProductId(), Location.ONLINE);
        return available >= quantity;
    }

    public boolean hasInventoryInNearbyStores(Product product, int quantity, Location currentLocation) {
        if (!isValidRequest(product, quantity) || currentLocation == null) return false;
        
        return locationService.getNearbyStores(currentLocation).stream()
            .map(store -> store.getStoreName().toUpperCase())
            .map(this::parseLocation)
            .filter(java.util.Objects::nonNull)
            .anyMatch(location -> hasStoreInventory(product.getProductId(), location, quantity));
    }
    
    private boolean isValidRequest(Product product, int quantity) {
        return product != null && product.getProductId() != null && quantity >= 0;
    }
    
    private Location parseLocation(String storeName) {
        try {
            return Location.valueOf(storeName);
        } catch (IllegalArgumentException e) {
            logger.debug("Store name '{}' doesn't match any Location enum", storeName);
            return null;
        }
    }
    
    private boolean hasStoreInventory(String productId, Location location, int quantity) {
        int available = getAvailableQuantity(productId, location);
        return available >= (MINIMUM_STORE_THRESHOLD + quantity);
    }
    
    // Method for testing purposes to access inventory data
    protected Map<String, Integer> getInventoryData() {
        return new ConcurrentHashMap<>(inventoryData);
    }
    
    // Method to get available quantity for a specific product and location
    public int getAvailableQuantity(String productId, Location location) {
        if (productId == null || location == null) {
            return 0;
        }
        
        String key = productId + ":" + location.name();
        return inventoryData.getOrDefault(key, 0);
    }
}