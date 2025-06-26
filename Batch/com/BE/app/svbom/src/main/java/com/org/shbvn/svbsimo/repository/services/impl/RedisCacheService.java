package com.org.shbvn.svbsimo.repository.services.impl;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheService.class);
    private final RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    public RedisCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    /**
     * Write-through cache with timestamp: save to Redis with expiration and timestamp metadata
     * @param key Cache key
     * @param value Object to cache
     * @param ttl Time to live in minutes
     */
    public void putWithTimestamp(String key, Object value, long ttl) {
        // Create a map to store both value and metadata
        Map<String, Object> dataWithMetadata = new HashMap<>();
        long now = Instant.now().getEpochSecond();
        
        dataWithMetadata.put("value", value);
        dataWithMetadata.put("createdAt", now);
        dataWithMetadata.put("expiresAt", now + (ttl * 60));
        dataWithMetadata.put("ttlMinutes", ttl);
        
        // Store the map in Redis
        redisTemplate.opsForValue().set(key, dataWithMetadata, ttl, TimeUnit.MINUTES);
        logger.debug("Stored key {} with TTL {} minutes and timestamp {}", key, ttl, now);
    }
    
    /**
     * Get value from cache with timestamp validation
     * @param key Cache key
     * @return Cached object or null if expired or not found
     */
    @SuppressWarnings("unchecked")
    public Object getWithTimestamp(String key) {
        Object cachedData = redisTemplate.opsForValue().get(key);
        
        if (cachedData instanceof Map) {
            try {
                Map<String, Object> dataWithMetadata = (Map<String, Object>) cachedData;
                
                // Extract metadata
                Object value = dataWithMetadata.get("value");
                Long expiresAt = (Long) dataWithMetadata.get("expiresAt");
                
                // Check if data has expired based on timestamp
                if (expiresAt != null) {
                    long now = Instant.now().getEpochSecond();
                    if (now >= expiresAt) {
                        logger.debug("Data for key {} has expired based on timestamp", key);
                        redisTemplate.delete(key);
                        return null;
                    }
                }
                
                return value;
            } catch (ClassCastException e) {
                logger.warn("Failed to extract timestamped data for key {}: {}", key, e.getMessage());
                return null;
            }
        }
        
        // If not using our timestamp format, return as is
        return cachedData;
    }
    
    /**
     * Get timestamp metadata for a key
     * @param key Cache key
     * @return Map with timestamp metadata or null if not found
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getTimestampMetadata(String key) {
        Object cachedData = redisTemplate.opsForValue().get(key);
        
        if (cachedData instanceof Map) {
            try {
                Map<String, Object> dataWithMetadata = (Map<String, Object>) cachedData;
                
                // Create a new map with just the metadata
                Map<String, Object> metadata = new HashMap<>();
                Long createdAt = (Long) dataWithMetadata.get("createdAt");
                Long expiresAt = (Long) dataWithMetadata.get("expiresAt");
                Long ttlMinutes = (Long) dataWithMetadata.get("ttlMinutes");
                
                if (createdAt != null && expiresAt != null) {
                    long now = Instant.now().getEpochSecond();
                    
                    metadata.put("createdAt", createdAt);
                    metadata.put("expiresAt", expiresAt);
                    metadata.put("ttlMinutes", ttlMinutes);
                    metadata.put("ageInSeconds", now - createdAt);
                    metadata.put("remainingTtl", Math.max(0, expiresAt - now));
                    metadata.put("isExpired", now >= expiresAt);
                    
                    // Add formatted dates for readability
                    metadata.put("createdAtDate", new Date(createdAt * 1000));
                    metadata.put("expiresAtDate", new Date(expiresAt * 1000));
                    
                    return metadata;
                }
            } catch (ClassCastException e) {
                logger.warn("Failed to extract timestamp metadata for key {}: {}", key, e.getMessage());
            }
        }
        
        return null;
    }
    
    /**
     * Check if data is expired based on timestamp
     * @param key Cache key
     * @return true if data exists and is not expired, false otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean isDataValidByTimestamp(String key) {
        Object cachedData = redisTemplate.opsForValue().get(key);
        
        if (cachedData instanceof Map) {
            try {
                Map<String, Object> dataWithMetadata = (Map<String, Object>) cachedData;
                Long expiresAt = (Long) dataWithMetadata.get("expiresAt");
                
                if (expiresAt != null) {
                    return Instant.now().getEpochSecond() < expiresAt;
                }
            } catch (ClassCastException e) {
                logger.warn("Failed to check timestamp validity for key {}: {}", key, e.getMessage());
            }
        }
        
        // If not using our timestamp format, fall back to checking if key exists
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    
    /**
     * Refresh the expiration time for a key with timestamp
     * @param key Cache key
     * @param newTtlMinutes New TTL in minutes
     * @return true if successful
     */
    @SuppressWarnings("unchecked")
    public boolean refreshExpirationWithTimestamp(String key, long newTtlMinutes) {
        Object cachedData = redisTemplate.opsForValue().get(key);
        
        if (cachedData instanceof Map) {
            try {
                Map<String, Object> dataWithMetadata = (Map<String, Object>) cachedData;
                Object value = dataWithMetadata.get("value");
                
                // Update the timestamp and TTL
                putWithTimestamp(key, value, newTtlMinutes);
                return true;
            } catch (ClassCastException e) {
                logger.warn("Failed to refresh expiration with timestamp for key {}: {}", key, e.getMessage());
            }
        }
        
        return false;
    }
    
    /**
     * Write-through cache: save to both Redis and DB
     * @param key Cache key
     * @param value Object to cache
     * @param ttl Time to live in minutes
     */
    public void put(String key, Object value, long ttl) {
        // redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.MINUTES);
        putWithTimestamp(key, value, ttl);
    }
    
    /**
     * Get value from cache
     * @param key Cache key
     * @return Cached object or null
     */
    public Object get(String key) {
        return getWithTimestamp(key);
    }
    
    /**
     * Remove from cache
     * @param key Cache key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
    
    /**
     * Check if key exists
     * @param key Cache key
     * @return true if exists
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    
    /**
     * Add to list
     * @param key List key
     * @param value Value to add
     */
    public void addToList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }
    
    /**
     * Get entire list
     * @param key List key
     * @return List of objects
     */
    public List<Object> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }
    
    /**
     * Delete multiple keys
     * @param keys Collection of keys to delete
     * @return Number of keys deleted
     */
    public Long deleteKeys(Set<String> keys) {
        return redisTemplate.delete(keys);
    }
    
    /**
     * Find keys matching a pattern
     * @param pattern Pattern to match (e.g., "user:*")
     * @return Set of matching keys
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
    
    /**
     * Increment a counter
     * @param key Counter key
     * @return New value
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }
    
    /**
     * Increment a counter by a specific amount
     * @param key Counter key
     * @param delta Amount to increment
     * @return New value
     */
    public Long incrementBy(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }
    
    /**
     * Set key expiration
     * @param key Cache key
     * @param ttl Time to live in minutes
     * @return true if successful
     */
    public Boolean expire(String key, long ttl) {
        return redisTemplate.expire(key, ttl, TimeUnit.MINUTES);
    }
    
    /**
     * Get key expiration
     * @param key Cache key
     * @return Time to live in minutes, or -1 if key doesn't exist or has no expiration
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MINUTES);
    }
    
    /**
     * Add to hash
     * @param key Hash key
     * @param hashKey Field name
     * @param value Value to add
     */
    public void putHash(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }
    
    /**
     * Get from hash
     * @param key Hash key
     * @param hashKey Field name
     * @return Value
     */
    public Object getHash(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }
    
    /**
     * Get entire hash
     * @param key Hash key
     * @return Map of hash entries
     */
    public Map<Object, Object> getEntireHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    
    /**
     * Delete from hash
     * @param key Hash key
     * @param hashKeys Field names
     * @return Number of fields removed
     */
    public Long deleteFromHash(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }
}
