package SHINHAN_PORTAL.REPORT_SIMO.domain.repository;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_25_UPDATE_TOCHUC_NGGL;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface API_1_25_UPDATE_TOCHUC_NGGL_Repository extends MongoRepository<API_1_25_UPDATE_TOCHUC_NGGL, String> {
    List<API_1_25_UPDATE_TOCHUC_NGGL> findByTemplateIDAndMonthYear(String templateID, String monthYear);
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
} 