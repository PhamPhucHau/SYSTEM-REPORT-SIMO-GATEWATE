package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UploadManagementDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.UploadManagementService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.UploadManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/upload-management")
public class UploadManagementController {

    @Autowired
    private UploadManagementService service;

    // Create
    @PostMapping
    public ResponseEntity<UploadManagement> create(@RequestBody UploadManagementDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // Read - by date range
    @GetMapping("/range")
    public ResponseEntity<List<UploadManagement>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        return ResponseEntity.ok(service.findByDateRange(start, end));
    }

    // Read - by username
    @GetMapping("/user/{username}")
    public ResponseEntity<List<UploadManagement>> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.findByUsername(username));
    }

    // Read - by template
    @GetMapping("/template/{templateID}")
    public ResponseEntity<List<UploadManagement>> findByTemplate(@PathVariable String templateID) {
        return ResponseEntity.ok(service.findByTemplate(templateID));
    }

    // Read - by action type
    @GetMapping("/action/{actionType}")
    public ResponseEntity<List<UploadManagement>> findByActionType(@PathVariable String actionType) {
        return ResponseEntity.ok(service.findByActionType(actionType));
    }

    // Read - flexible search (date range required, filters optional)
    @GetMapping("/search")
    public ResponseEntity<List<UploadManagement>> search(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,
            @RequestParam(required = false) String templateID,
            @RequestParam(required = false) String actionType) {
        return ResponseEntity.ok(service.search(start, end, templateID, actionType));
    }
}


