package SHINHAN_PORTAL.REPORT_SIMO.domain.enums;

public enum TemplateID {
    API_1_6_TTDS_TKTT_DK,
    API_1_7_TTDS_TKTT_NNGL,
    API_1_9_UPDATE_TTDS_TKTT_DK,
    API_1_8_UPDATE_TTDS_TKTT_NNGL;

    public static TemplateID fromString(String templateId) {
        try {
            return valueOf(templateId.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid template ID: " + templateId);
        }
    }
}
