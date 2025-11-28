package SHINHAN_PORTAL.REPORT_SIMO.domain.repository;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.UploadManagement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UploadManagementRepository extends MongoRepository<UploadManagement, String> {

    // Tìm theo khoảng thời gian
    @Query(value="{'timestamp': {$gte: ?0, $lte: ?1}}",sort ="{'timestamp': -1}")
    List<UploadManagement> findByTimestampBetween(Date start, Date end);

    // Lọc theo các trường phổ biến
    List<UploadManagement> findByUsernameOrderByTimestampDesc(String username);
    List<UploadManagement> findByTemplateIDOrderByTimestampDesc(String templateID);
    List<UploadManagement> findByActionTypeOrderByTimestampDesc(String actionType);

    // Kết hợp điều kiện: thời gian + các trường (phổ biến)
    @Query(value="{'timestamp': {$gte: ?0, $lte: ?1}, 'templateID': ?2}",sort ="{'timestamp': -1}")
    List<UploadManagement> findByTimestampBetweenAndTemplate(Date start, Date end, String templateID);

    @Query(value="{'timestamp': {$gte: ?0, $lte: ?1}, 'username': ?2}",sort ="{'timestamp': -1}")
    List<UploadManagement> findByTimestampBetweenAndUsername(Date start, Date end, String username);

    @Query(value="{'timestamp': {$gte: ?0, $lte: ?1}, 'templateID': ?2, 'actionType': ?3}",sort ="{'timestamp': -1}")
    List<UploadManagement> searchByTemplateAndAction(Date start, Date end, String templateID, String actionType);

    @Query(value="{'timestamp': {$gte: ?0, $lte: ?1}, 'actionType': ?2}",sort ="{'timestamp': -1}")
    List<UploadManagement> findByTimestampBetweenAndActionType(Date start, Date end, String actionType);
}


