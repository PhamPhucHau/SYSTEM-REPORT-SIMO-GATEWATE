package com.org.shbvn.svbsimo.repository.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;

/**
 * Generic write-through caching service using Redis
 * Provides type-safe caching with automatic serialization/deserialization
 */
@Service
public class GenericCacheService {
    
    private static final Logger logger = LoggerFactory.getLogger(GenericCacheService.class);
    
    private final RedisCacheService redisCacheService;
    
    @Autowired
    public GenericCacheService(RedisCacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
    }
    
    /**
     * Get value from cache, or compute and cache if not present
     * 
     * @param <T> Type of cached value
     * @param key Cache key
     * @param supplier Function to compute value if not in cache
     * @param ttl Time to live in seconds
     * @param type Class of the value type
     * @return Cached or computed value
     */
    @SuppressWarnings("unchecked")
    public <T> T getOrCompute(String key, Supplier<T> supplier, long ttl, Class<T> type) {
        // Try to get from cache
        Object cachedValue = redisCacheService.get(key);
        
        try {
            logger.info("cachedValue : {}" ,CommonUtils.toJson(cachedValue));
        } catch (ServiceRuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (cachedValue != null) {
            try {
                // If value exists in cache and is of correct type, return it
                if (type.isInstance(cachedValue)) {
                    logger.debug("Cache hit for key: {}", key);
                    return (T) cachedValue;
                }
            } catch (Exception e) {
                logger.warn("Error deserializing cached value for key: {}", key, e);
            }
        }
        
        // Cache miss or deserialization error, compute value
        logger.debug("Cache miss for key: {}", key);
        T value = supplier.get();
        
        // Cache the computed value if not null
        if (value != null) {
            redisCacheService.put(key, value, ttl);
        }
        
        return value;
    }
    
    /**
     * Get value from cache, or compute and cache if not present (with default TTL)
     * 
     * @param <T> Type of cached value
     * @param key Cache key
     * @param supplier Function to compute value if not in cache
     * @param type Class of the value type
     * @return Cached or computed value
     */
    public <T> T getOrCompute(String key, Supplier<T> supplier, Class<T> type) {
        // Default TTL: 1 hour
        return getOrCompute(key, supplier, 3600, type);
    }
    
    /**
     * Get Optional value from cache, or compute and cache if not present
     * 
     * @param <T> Type of cached value
     * @param key Cache key
     * @param supplier Function to compute Optional value if not in cache
     * @param ttl Time to live in seconds
     * @param type Class of the value type
     * @return Optional containing cached or computed value
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getOrComputeOptional(String key, Supplier<Optional<T>> supplier, long ttl, Class<T> type) {
        // Try to get from cache
        Object cachedValue = redisCacheService.get(key);
        
        if (cachedValue != null) {
            try {
                // Special handling for null value (representing empty Optional)
                if (cachedValue.equals("__NULL__")) {
                    return Optional.empty();
                }
                
                // If value exists in cache and is of correct type, return it
                if (type.isInstance(cachedValue)) {
                    logger.debug("Cache hit for key: {}", key);
                    return Optional.of((T) cachedValue);
                }
            } catch (Exception e) {
                logger.warn("Error deserializing cached value for key: {}", key, e);
            }
        }
        
        // Cache miss or deserialization error, compute value
        logger.debug("Cache miss for key: {}", key);
        Optional<T> optionalValue = supplier.get();
        
        // Cache the computed value
        if (optionalValue.isPresent()) {
            redisCacheService.put(key, optionalValue.get(), ttl);
        } else {
            // Store a special marker for empty Optional
            redisCacheService.put(key, "__NULL__", ttl);
        }
        
        return optionalValue;
    }
    
    /**
     * Get Optional value from cache, or compute and cache if not present (with default TTL)
     * 
     * @param <T> Type of cached value
     * @param key Cache key
     * @param supplier Function to compute Optional value if not in cache
     * @param type Class of the value type
     * @return Optional containing cached or computed value
     */
    public <T> Optional<T> getOrComputeOptional(String key, Supplier<Optional<T>> supplier, Class<T> type) {
        // Default TTL: 1 hour
        return getOrComputeOptional(key, supplier, 3600, type);
    }
    
    /**
     * Get list from cache, or compute and cache if not present
     * 
     * @param <T> Type of list elements
     * @param key Cache key
     * @param supplier Function to compute list if not in cache
     * @param ttl Time to live in seconds
     * @return Cached or computed list
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getOrComputeList(String key, Supplier<List<T>> supplier, long ttl) {
        // Try to get from cache
        Object cachedValue = redisCacheService.get(key);
        
        if (cachedValue != null && cachedValue instanceof List) {
            logger.debug("Cache hit for key: {}", key);
            return (List<T>) cachedValue;
        }
        
        // Cache miss, compute value
        logger.debug("Cache miss for key: {}", key);
        List<T> list = supplier.get();
        
        // Cache the computed list if not null or empty
        if (list != null && !list.isEmpty()) {
            redisCacheService.put(key, list, ttl);
        }
        
        return list;
    }
    
    /**
     * Get list from cache, or compute and cache if not present (with default TTL)
     * 
     * @param <T> Type of list elements
     * @param key Cache key
     * @param supplier Function to compute list if not in cache
     * @return Cached or computed list
     */
    public <T> List<T> getOrComputeList(String key, Supplier<List<T>> supplier) {
        // Default TTL: 1 hour
        return getOrComputeList(key, supplier, 3600);
    }
    
    /**
     * Invalidate a cache entry
     * 
     * @param key Cache key to invalidate
     */
    public void invalidate(String key) {
        redisCacheService.delete(key);
    }
    
    /**
     * Check if a key exists in the cache
     * 
     * @param key Cache key to check
     * @return true if the key exists
     */
    public boolean exists(String key) {
        return redisCacheService.hasKey(key);
    }

    public RedisCacheService getRedisCacheService() {
        return redisCacheService;
    }
}