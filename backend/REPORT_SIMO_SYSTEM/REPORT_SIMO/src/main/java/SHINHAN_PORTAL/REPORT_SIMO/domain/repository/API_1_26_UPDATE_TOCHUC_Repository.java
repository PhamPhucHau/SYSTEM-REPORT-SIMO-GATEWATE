package SHINHAN_PORTAL.REPORT_SIMO.domain.repository;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_26_UPDATE_TOCHUC;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface API_1_26_UPDATE_TOCHUC_Repository extends MongoRepository<API_1_26_UPDATE_TOCHUC, String> {
    List<API_1_26_UPDATE_TOCHUC> findByTemplateIDAndMonthYearAndStatus(String templateID, String monthYear, String status);
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
} 