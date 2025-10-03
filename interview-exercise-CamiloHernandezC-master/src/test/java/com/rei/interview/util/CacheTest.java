package com.rei.interview.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    private Cache<String, String> cache;

    @BeforeEach
    void setUp() {
        cache = new Cache<>();
    }

    @Test
    void shouldAllowPutWhenBelowMaxSize() {
        String result = cache.put("key1", "value1");
        assertNull(result);
        assertEquals("value1", cache.get("key1"));
        assertEquals(1, cache.size());
    }

    @Test
    void shouldRejectPutWhenAtMaxSize() {
        for (int i = 0; i < 1000; i++) {
            cache.put("key" + i, "value" + i);
        }
        String result = cache.put("overflow", "overflow_value");
        assertNull(result);
        assertEquals(1000, cache.size());
    }

    @Test
    void shouldBehaveLikeConcurrentHashMapForOtherOperations() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        
        assertTrue(cache.containsKey("key1"));
        String removed = cache.remove("key1");
        assertEquals("value1", removed);
        assertEquals(1, cache.size());
    }


}