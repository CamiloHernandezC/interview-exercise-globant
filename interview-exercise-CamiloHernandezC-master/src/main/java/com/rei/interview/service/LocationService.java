package com.rei.interview.service;

import com.rei.interview.model.Location;
import com.rei.interview.model.Store;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    public List<Store> getNearbyStores(Location location) {
        return List.of(new Store("Seattle"), new Store("Issaquah"));
    }
}