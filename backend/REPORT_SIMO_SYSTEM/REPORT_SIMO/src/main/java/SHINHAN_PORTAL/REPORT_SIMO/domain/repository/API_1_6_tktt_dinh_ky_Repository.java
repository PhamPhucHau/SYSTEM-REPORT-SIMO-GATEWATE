package SHINHAN_PORTAL.REPORT_SIMO.domain.repository;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_6_tktt_dinh_ky;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface API_1_6_tktt_dinh_ky_Repository extends MongoRepository<API_1_6_tktt_dinh_ky, String> {
    List<API_1_6_tktt_dinh_ky> findByTemplateIDAndMonthYear(String templateID, String monthYear);
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
}

