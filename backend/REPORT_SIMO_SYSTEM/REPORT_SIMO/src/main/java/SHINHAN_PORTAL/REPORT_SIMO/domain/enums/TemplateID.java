package SHINHAN_PORTAL.REPORT_SIMO.domain.enums;

public enum TemplateID {
    API_1_6_TTDS_TKTT_DK,
    API_1_7_TTDS_TKTT_NNGL,
    API_1_9_UPDATE_TTDS_TKTT_DK,
    API_1_8_UPDATE_TTDS_TKTT_NNGL,
    API_1_27_TT_DVCNTT,
    API_1_28_TT_DVCNTT_NGGL,
    API_1_29_UPDATE_DVCNTT_NGGL,
    API_1_30_UPDATE_DVCNTT;
    public static TemplateID fromString(String templateId) {
        try {
            return valueOf(templateId.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid template ID: " + templateId);
        }
    }
}
