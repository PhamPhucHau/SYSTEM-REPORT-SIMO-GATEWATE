package SHINHAN_PORTAL.REPORT_SIMO.domain.enums;

public enum TemplateID {
    API_1_6_TTDS_TKTT_DK,
    API_1_7_TTDS_TKTT_NNGL,
    API_1_8_UPDATE_TTDS_TKTT_NNGL,
    API_1_9_UPDATE_TTDS_TKTT_DK,
    API_1_23_TTDS_TKTT_TC_DK,
    API_1_24_TTDS_TKTT_TC_NGGL,
    API_1_25_UPDATE_TTDS_TKTT_TC_NGGL,
    API_1_26_UPDATE_TTDS_TKTT_TC,
    API_1_27_TT_DVCNTT,
    API_1_28_TT_DVCNTT_NGGL,
    API_1_29_UPDATE_DVCNTT_NGGL,
    API_1_30_UPDATE_DVCNTT,
    API_1_31_TT_TNH,
    API_1_32_TT_TNH_NGGL,
    API_1_33_UPDATE_TNH_NGGL,
    API_1_34_UPDATE_TNH;
    public static TemplateID fromString(String templateId) {
        try {
            return valueOf(templateId.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid template ID: " + templateId);
        }
    }
}
