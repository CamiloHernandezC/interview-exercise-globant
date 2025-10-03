package com.rei.interview.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache<K, V> extends ConcurrentHashMap<K, V> {

    private static final int MAX_SIZE = 1000;

    @Override
    public V put(K key, V value) {
        validateKey(key);
        return (super.size() < MAX_SIZE || super.containsKey(key)) 
                ? super.put(key, value) 
                : null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.keySet().forEach(this::validateKey);
        
        long newEntries = m.keySet().stream()
                .filter(key -> !super.containsKey(key))
                .count();
        
        if (super.size() + newEntries <= MAX_SIZE) {
            super.putAll(m);
        }
    }

    @Override
    public V get(Object key) {
        return key != null ? super.get(key) : null;
    }

    public static int getMaxSize() {
        return MAX_SIZE;
    }
    
    private void validateKey(K key) {
        if (key == null) {
            throw new NullPointerException("Cache does not permit null keys");
        }
    }
}
