# E-Commerce Microservice API
====================

This project contains an optimized e-commerce microservice application built with **Spring Boot 3.1.10**. The application provides REST services for product management, shopping cart, inventory, and store locations.

## Key Features

- **Complete REST API** for e-commerce operations
- **Microservice architecture** with clear separation of concerns
- **Modern dependency injection** using constructor injection
- **Optimized test suite** with full coverage
- **Real-time inventory management**
- **Location services** for nearby stores

## System Requirements

### **Java**
- [JDK 17](https://aws.amazon.com/corretto/) or higher

### **Maven**
- [Maven 3.6.1+](https://maven.apache.org/download.cgi)

## Running the Application

### Option 1: Command Line
```bash
mvn spring-boot:run
```

### Option 2: IDE
Run the `Application.java` class directly

### Verification
The application will be available at: **http://localhost:8080**


## Service Architecture

### **Core Services**

| Service | Responsibility | Endpoints |
|---------|---------------|-----------|
| **ProductService** | Product management, validation and retrieval by ID | `/products/*` |
| **CartService** | Shopping cart operations | `/cart/*` |
| **LocationService** | Search for nearby stores by location | `/locations/*` |
| **InventoryService** | Inventory verification in stores and distribution centers | `/inventory/*` |
| **DistributionCenterService** | Distribution center inventory management | Internal |

### **Available REST APIs**

#### **CartWebService**
Main service for shopping cart interactions:

- **POST** `/cart/add` - Add product to cart
- **GET** `/cart/{id}` - Get cart information
- **Response Format**: JSON with `CartDto`

#### **ProductController** 
Endpoints for product management:

- **GET** `/products` - List all products
- **GET** `/products/{id}` - Get specific product
- **Response Format**: JSON with `ProductDto`

## Testing

### **Optimized Test Suite**
- **17 essential tests** (reduced from 160+ original)
- **Complete coverage** of critical functionality
- **Fast and reliable** execution

```bash
# Run tests
mvn test

# Compile and test
mvn clean compile test
```

### **Included Tests**
- `ProductControllerTest` (6 tests) - REST Endpoints
- `ProductServiceTest` (5 tests) - Business Logic  
- `ProductRepositoryTest` (3 tests) - Data Access
- `CacheTest` (3 tests) - Cache Utilities

## Project Configuration

### **Directory Structure**
```
src/
├── main/java/com/rei/interview/
│   ├── Application.java           # Spring Boot entry point
│   ├── checkout/                  # Cart and checkout services
│   ├── inventory/                 # Inventory management
│   ├── location/                  # Location services
│   ├── product/                   # Product management
│   ├── rs/                        # REST Controllers and DTOs
│   ├── search/                    # Search functionality
│   ├── ui/                        # UI Controllers
│   └── util/                      # Utilities and cache
└── main/resources/
    ├── application.yml            # Spring configuration
    ├── products.csv              # Product data
    └── product_inventory.csv     # Inventory data
```

### **Technologies Used**
- **Spring Boot 3.1.10** - Main framework
- **Spring Web** - REST APIs
- **Spring Data** - Data access  
- **JUnit 5** - Testing framework
- **Mockito** - Mocking for tests
- **Maven** - Dependency management

## Deployment and Production

### **Environment Variables**
```yaml
# application.yml
server:
  port: 8080
  
spring:
  profiles:
    active: default
```

### **Health Check**
- **URL**: `http://localhost:8080/actuator/health`
- **Status**: Automatic Spring Boot monitoring

## Implemented Optimizations

### **Code Quality**
-  **Clean Architecture** - Clear separation of concerns
-  **Modern Patterns** - Spring Boot best practices
-  **Complete Documentation** - APIs and services documented




