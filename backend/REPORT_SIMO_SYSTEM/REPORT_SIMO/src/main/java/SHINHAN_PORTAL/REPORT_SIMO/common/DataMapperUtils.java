package SHINHAN_PORTAL.REPORT_SIMO.common;

import org.springframework.stereotype.Component;


@Component
public class DataMapperUtils {

    public static String safeString(Object value) {
        return safeString(value, "");
    }

    public static String safeString(Object value, String defaultValue) {
        return safeString(value, defaultValue, false);
    }

    public static String safeString(Object value, String defaultValue, boolean nullIfEmpty) {
        if (value == null) return defaultValue;
        String result = String.valueOf(value);
        if (nullIfEmpty && result.trim().isEmpty()) return null;
        return result;
    }

    public static Integer safeInteger(Object value, Integer defaultValue) {
        if (value == null) return defaultValue;
        if (value instanceof Number) return ((Number) value).intValue();
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
}