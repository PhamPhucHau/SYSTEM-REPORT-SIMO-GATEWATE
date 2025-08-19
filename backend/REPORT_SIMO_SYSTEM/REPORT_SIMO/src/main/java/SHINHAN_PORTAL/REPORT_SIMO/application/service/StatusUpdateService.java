package SHINHAN_PORTAL.REPORT_SIMO.application.service;

public interface StatusUpdateService {
	long updateStatus(String templateID, String monthYear, String oldStatus, String newStatus);
} 