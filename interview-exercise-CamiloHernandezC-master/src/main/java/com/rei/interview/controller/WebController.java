package com.rei.interview.controller;

import com.rei.interview.service.CartService;
import com.rei.interview.service.InventoryService;
import com.rei.interview.service.LocationService;
import com.rei.interview.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private ProductService productService;
    private LocationService locationService;
    private InventoryService inventoryService;
    private CartService cartService;


    public WebController(
            ProductService productService,
            LocationService locationService,
            InventoryService inventoryService,
            CartService cartService) {
        this.productService = productService;
        this.locationService = locationService;
        this.inventoryService = inventoryService;
        this.cartService = cartService;

    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }
}