package SHINHAN_PORTAL.REPORT_SIMO.domain.repository;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.LIST_FILE_UPLOAD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  List_File_Upload_Repository extends MongoRepository<LIST_FILE_UPLOAD, String> {
    Page<LIST_FILE_UPLOAD> findByTemplateIDAndMonthYear(String templateID, String monthYear, Pageable pageable);

    Page<LIST_FILE_UPLOAD> findByTemplateID(String templateID, Pageable pageable);

    Page<LIST_FILE_UPLOAD> findByMonthYear(String monthYear, Pageable pageable);

    Page<LIST_FILE_UPLOAD> findAll(Pageable pageable);
}
