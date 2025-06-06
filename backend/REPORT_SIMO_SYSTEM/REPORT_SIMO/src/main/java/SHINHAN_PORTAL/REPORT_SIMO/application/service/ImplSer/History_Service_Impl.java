package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.HistoryService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.LIST_FILE_UPLOAD;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.List_File_Upload_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class History_Service_Impl implements HistoryService {
    @Autowired
    private List_File_Upload_Repository listFileUploadRepository ;
    @Override
    public LIST_FILE_UPLOAD saveUploadHistory( LIST_FILE_UPLOAD history) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        history.setInf_regis_dt(LocalDate.now().format(dateFormatter));
        history.setInf_regis_time(LocalTime.now().format(timeFormatter));
        return listFileUploadRepository.save(history);
    }

    @Override
    public Page<LIST_FILE_UPLOAD> getFiles(String templateID, String monthYear, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (templateID != null && monthYear != null) {
            return listFileUploadRepository.findByTemplateIDAndMonthYear(templateID, monthYear, pageable);
        } else if (templateID != null) {
            return listFileUploadRepository.findByTemplateID(templateID, pageable);
        } else if (monthYear != null) {
            return listFileUploadRepository.findByMonthYear(monthYear, pageable);
        } else {
            return listFileUploadRepository.findAll(pageable);
        }
    }
}
