package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.exception.ResourceNotFoundException;
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
import java.util.Optional;

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
        history.setInf_chg_dt(LocalDate.now().format(dateFormatter));
        history.setInf_chg_time(LocalTime.now().format(timeFormatter));
        return listFileUploadRepository.save(history);
    }

    @Override
    public Page<LIST_FILE_UPLOAD> getFiles(String templateID, String monthYear,String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        System.out.println(username.isEmpty());
        if(templateID != null && monthYear != null&& (username != null&& !username.isEmpty()))
        {
            return listFileUploadRepository.findByTemplateIDAndMonthYearAndUsername(templateID, monthYear, username, pageable);
        }else if (templateID != null && monthYear != null) {
            return listFileUploadRepository.findByTemplateIDAndMonthYear(templateID, monthYear, pageable);
        } else if (templateID != null) {
            return listFileUploadRepository.findByTemplateID(templateID, pageable);
        } else if (monthYear != null) {
            return listFileUploadRepository.findByMonthYear(monthYear, pageable);
        } else {
            return listFileUploadRepository.findAll(pageable);
        }
    }
    @Override
    public LIST_FILE_UPLOAD updateById(String id, String data_ledg_s ) {
    Optional<LIST_FILE_UPLOAD> existingData = listFileUploadRepository.findById(id);
    if (existingData.isPresent()) {
        LIST_FILE_UPLOAD entity = existingData.get();
        
        // Cập nhật các trường cần thay đổi
        entity.setData_ledg_s(data_ledg_s);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        entity.setInf_chg_dt(LocalDate.now().format(dateFormatter));
        entity.setInf_chg_time(LocalTime.now().format(timeFormatter));

        return listFileUploadRepository.save(entity);
    } else {
        throw new ResourceNotFoundException("Không tìm thấy bản ghi với id: " + id);
    }
    }
    @Override
    public LIST_FILE_UPLOAD findById(String id, String data_ledg_s ) {
        Optional<LIST_FILE_UPLOAD> existingData = listFileUploadRepository.findByIdAndDataLedgS(id, data_ledg_s);
        if (existingData.isPresent()) {
            return existingData.get();
        } else {
            throw new ResourceNotFoundException("Không tìm thấy bản ghi với id: " + id + " và data_ledg_s: " + data_ledg_s);
        }
    }
}
