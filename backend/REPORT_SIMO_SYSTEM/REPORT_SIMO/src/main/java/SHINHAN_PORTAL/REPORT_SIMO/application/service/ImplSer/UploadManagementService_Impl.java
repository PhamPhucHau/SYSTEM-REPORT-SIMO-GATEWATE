package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UploadManagementDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.UploadManagementService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.UploadManagement;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.UploadManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UploadManagementService_Impl implements UploadManagementService {

    @Autowired
    private UploadManagementRepository repository;

    @Override
    public UploadManagement create(UploadManagementDTO dto) {
        UploadManagement entity = new UploadManagement();
        entity.setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : new Date());
        entity.setUsername(dto.getUsername());
        entity.setUserRole(dto.getUserRole());
        entity.setTemplateID(dto.getTemplateID());
        entity.setMonthYear(dto.getMonthYear());
        entity.setActionType(dto.getActionType());
        entity.setFileName(dto.getFileName());
        entity.setFilePath(dto.getFilePath());
        entity.setStatusBefore(dto.getStatusBefore());
        entity.setStatusAfter(dto.getStatusAfter());
        entity.setDescription(dto.getDescription());
        entity.setSimoEndpoint(dto.getSimoEndpoint());
        entity.setSimoResponseStatus(dto.getSimoResponseStatus());
        entity.setErrorMessage(dto.getErrorMessage());
        entity.setRequestId(dto.getRequestId());
        entity.setCorrelationId(dto.getCorrelationId());
        return repository.save(entity);
    }

    @Override
    public List<UploadManagement> findByDateRange(Date start, Date end) {
        return repository.findByTimestampBetween(start, end);
    }

    @Override
    public List<UploadManagement> findByUsername(String username) {
        return repository.findByUsernameOrderByTimestampDesc(username);
    }

    @Override
    public List<UploadManagement> findByTemplate(String templateID) {
        return repository.findByTemplateIDOrderByTimestampDesc(templateID);
    }

    @Override
    public List<UploadManagement> findByActionType(String actionType) {
        return repository.findByActionTypeOrderByTimestampDesc(actionType);
    }

    @Override
    public List<UploadManagement> searchByTemplateAndAction(Date start, Date end, String templateID, String actionType) {
        return repository.searchByTemplateAndAction(start, end, templateID, actionType);
    }

    @Override
    public List<UploadManagement> search(Date start, Date end, String templateID, String actionType) {
        if (start == null || end == null) {
            // If no date range, fallback to basic filters or return empty depending on policy
            if (templateID != null && !templateID.isEmpty() && actionType != null && !actionType.isEmpty()) {
                return repository.searchByTemplateAndAction(new Date(0), new Date(), templateID, actionType);
            }
            if (templateID != null && !templateID.isEmpty()) {
                return repository.findByTemplateIDOrderByTimestampDesc(templateID);
            }
            if (actionType != null && !actionType.isEmpty()) {
                return repository.findByActionTypeOrderByTimestampDesc(actionType);
            }
            return repository.findByTimestampBetween(new Date(0), new Date());
        }
        if (templateID != null && !templateID.isEmpty() && actionType != null && !actionType.isEmpty()) {
            return repository.searchByTemplateAndAction(start, end, templateID, actionType);
        }
        if (templateID != null && !templateID.isEmpty()) {
            return repository.findByTimestampBetweenAndTemplate(start, end, templateID);
        }
        if (actionType != null && !actionType.isEmpty()) {
            return repository.findByTimestampBetweenAndActionType(start, end, actionType);
        }
        return repository.findByTimestampBetween(start, end);
    }
}


