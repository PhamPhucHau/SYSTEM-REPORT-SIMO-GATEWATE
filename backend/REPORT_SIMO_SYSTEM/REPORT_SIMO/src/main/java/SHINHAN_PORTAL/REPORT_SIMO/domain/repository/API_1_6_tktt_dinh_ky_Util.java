package SHINHAN_PORTAL.REPORT_SIMO.domain.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class API_1_6_tktt_dinh_ky_Util {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Update status từ "00" sang "90"
     * @param templateID
     * @param monthYear
     * @param old_status
     * @param new_status
     * @return số lượng document được update
     */
    public long updateStatus(String templateID, String monthYear, String old_status, String new_status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("templateID").is(templateID)
                .and("monthYear").is(monthYear)
                .and("status").is(old_status)); // chỉ update khi đang là "00"

        Update update = new Update();
        update.set("status", new_status);

        return mongoTemplate.updateMulti(query, update, "API_1_6_tktt_dinh_ky").getModifiedCount();
    }
}

