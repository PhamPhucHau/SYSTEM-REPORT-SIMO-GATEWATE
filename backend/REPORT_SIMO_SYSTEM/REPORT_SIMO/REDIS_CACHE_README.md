# Redis Cache Implementation cho SIMO System

## Tổng quan

Dự án đã được tích hợp Redis cache để cải thiện hiệu suất, đặc biệt là cho Template operations.

## Cấu trúc Cache

### 1. Cache Names
- `templates`: Cache cho Template entities (TTL: 2 giờ)
- `users`: Cache cho User entities (TTL: 30 phút)
- Default cache: TTL 1 giờ

### 2. Cache Keys
- `templates::#id`: Template theo ID
- `templates::#templateId`: Template theo templateID
- `templates::all`: Tất cả templates
- `users::#id`: User theo ID

## Cài đặt và Chạy

### 1. Khởi động Redis với Docker
```bash
# Chạy Redis và Redis Commander
docker-compose -f docker-compose.redis.yml up -d

# Kiểm tra Redis
docker ps
```

### 2. Truy cập Redis Commander
- URL: http://localhost:8082
- Không cần authentication

### 3. Cấu hình Application
Các cấu hình Redis đã được thêm vào `application.properties`:
```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.type=redis
spring.cache.redis.time-to-live=3600000
```

## API Endpoints

### Cache Management APIs

#### 1. Xóa tất cả cache
```http
DELETE /api/cache/clear-all
```

#### 2. Xóa cache theo tên
```http
DELETE /api/cache/clear/{cacheName}
```

#### 3. Xóa key cụ thể
```http
DELETE /api/cache/evict/{cacheName}/{key}
```

#### 4. Lấy thống kê cache
```http
GET /api/cache/stats
```

#### 5. Kiểm tra Redis key
```http
GET /api/cache/redis/key/{key}
```

#### 6. Xóa Redis key
```http
DELETE /api/cache/redis/key/{key}
```

## Sử dụng Cache trong Code

### 1. Cache Annotations

#### @Cacheable
```java
@Cacheable(value = "templates", key = "#id")
public TemplateDTO getById(String id) {
    // Logic truy vấn database
}
```

#### @CacheEvict
```java
@CacheEvict(value = "templates", allEntries = true)
public TemplateDTO create(TemplateDTO dto) {
    // Logic tạo mới
}
```

### 2. CacheUtil Service
```java
@Autowired
private CacheUtil cacheUtil;

// Xóa cache
cacheUtil.clearCache("templates");

// Set giá trị với TTL
cacheUtil.setWithTTL("key", value, 3600);

// Kiểm tra key
boolean exists = cacheUtil.hasKey("key");
```

## Monitoring và Debugging

### 1. Logs
Cache operations được log với level INFO:
```
Fetching template from database with id: 123
Cache 'templates' cleared successfully
```

### 2. Redis Commander
- Truy cập: http://localhost:8082
- Xem keys, values, và thống kê Redis

### 3. Application Metrics
```http
GET /api/cache/stats
```

## Best Practices

### 1. Cache Strategy
- **Read-heavy data**: Sử dụng cache nhiều
- **Write-heavy data**: Cẩn thận với cache invalidation
- **Critical data**: Có fallback mechanism

### 2. TTL Configuration
- Template: 2 giờ (ít thay đổi)
- User: 30 phút (thay đổi vừa phải)
- Default: 1 giờ

### 3. Cache Invalidation
- Sử dụng `@CacheEvict` khi update/delete
- Clear all entries khi cần thiết
- Monitor cache hit/miss ratio

### 4. Error Handling
- Redis connection failures được handle gracefully
- Application vẫn hoạt động khi Redis down
- Fallback to database queries

## Troubleshooting

### 1. Redis Connection Issues
```bash
# Kiểm tra Redis container
docker logs simo-redis

# Test connection
redis-cli ping
```

### 2. Cache Performance
- Monitor cache hit ratio
- Adjust TTL values
- Review cache keys strategy

### 3. Memory Usage
- Monitor Redis memory usage
- Set maxmemory policy
- Configure eviction policy

## Production Deployment

### 1. Redis Configuration
```properties
# Production settings
spring.data.redis.host=${REDIS_HOST:localhost}
spring.data.redis.port=${REDIS_PORT:6379}
spring.data.redis.password=${REDIS_PASSWORD:}
spring.data.redis.ssl=${REDIS_SSL:false}
```

### 2. Security
- Enable Redis authentication
- Use SSL/TLS
- Restrict network access

### 3. High Availability
- Redis Cluster
- Redis Sentinel
- Backup strategies 