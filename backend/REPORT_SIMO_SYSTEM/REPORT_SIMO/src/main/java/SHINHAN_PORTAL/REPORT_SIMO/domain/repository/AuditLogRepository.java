package SHINHAN_PORTAL.REPORT_SIMO.domain.repository;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    
    // Tìm log theo user
    List<AuditLog> findByUserIdOrderByTimestampDesc(String userId);
    
    // Tìm log theo username
    List<AuditLog> findByUsernameOrderByTimestampDesc(String username);
    
    // Tìm log theo action type
    List<AuditLog> findByActionTypeOrderByTimestampDesc(String actionType);
    
    // Tìm log theo resource type
    List<AuditLog> findByResourceTypeOrderByTimestampDesc(String resourceType);
    
    // Tìm log theo khoảng thời gian - Sử dụng @Query để đảm bảo query đúng
    @Query("{'timestamp': {$gte: ?0, $lte: ?1}}")
    List<AuditLog> findByTimestampBetweenOrderByTimestampDesc(Date startDate, Date endDate);
    
    // Tìm log theo user và khoảng thời gian
    @Query("{'userId': ?0, 'timestamp': {$gte: ?1, $lte: ?2}}")
    List<AuditLog> findByUserIdAndTimestampBetweenOrderByTimestampDesc(String userId, Date startDate, Date endDate);
    
    // Tìm log theo endpoint
    List<AuditLog> findByEndpointOrderByTimestampDesc(String endpoint);
    
    // Tìm log theo IP address
    List<AuditLog> findByIpAddressOrderByTimestampDesc(String ipAddress);
    
    // Custom query để tìm kiếm nâng cao
    @Query("{'$or': [{'username': {$regex: ?0, $options: 'i'}}, {'description': {$regex: ?0, $options: 'i'}}]}")
    List<AuditLog> findByUsernameOrDescriptionContaining(String searchTerm);
    
    // Lấy tất cả logs với sắp xếp theo timestamp giảm dần
    @Query("{}")
    List<AuditLog> findAllOrderByTimestampDesc();
} 