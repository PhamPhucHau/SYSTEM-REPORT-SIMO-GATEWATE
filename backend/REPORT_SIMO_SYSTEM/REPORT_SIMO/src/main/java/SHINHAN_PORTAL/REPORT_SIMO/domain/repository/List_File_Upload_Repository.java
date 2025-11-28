package SHINHAN_PORTAL.REPORT_SIMO.domain.repository;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.LIST_FILE_UPLOAD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  List_File_Upload_Repository extends MongoRepository<LIST_FILE_UPLOAD, String> {
    Page<LIST_FILE_UPLOAD> findByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear,String username, Pageable pageable);

    Page<LIST_FILE_UPLOAD> findByTemplateIDAndMonthYear(String templateID, String monthYear, Pageable pageable);

    Page<LIST_FILE_UPLOAD> findByTemplateID(String templateID, Pageable pageable);

    Page<LIST_FILE_UPLOAD> findByMonthYear(String monthYear, Pageable pageable);

    Page<LIST_FILE_UPLOAD> findAll(Pageable pageable);
    @Query("{ '_id': ?0, 'data_ledg_s': ?1 }")
    Optional<LIST_FILE_UPLOAD> findByIdAndDataLedgS(String id, String data_ledg_s);

    // Find multiple files by IDs and status
    @Query("{ '_id': { $in: ?0 }, 'data_ledg_s': ?1 }")
    List<LIST_FILE_UPLOAD> findByIdInAndDataLedgS(List<String> ids, String data_ledg_s);

}
