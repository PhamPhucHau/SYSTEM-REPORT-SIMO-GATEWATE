package com.org.shbvn.svbsimo.core.common;


import com.org.shbvn.svbsimo.core.utils.DateUtils;

/**
 * Generic class to handle audit information fields
 * Used for setting registration and last change information
 */
public class AuditInfoDTO {
    
    /**
     * Sets audit information for a new entity
     * @param entity The entity to set audit information on
     * @param username The username of the creator
     * @param <T> The type of entity
     * @return The entity with audit information set
     */
    public static <T> T setCreationInfo(T entity, String username) {
        String currentDateTime = DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss);
        try {
            // Set registration info
            if (hasProperty(entity, "regisUser")) {
                entity.getClass().getMethod("setRegisUser", String.class).invoke(entity, username);
            }
            if (hasProperty(entity, "regisInfDt")) {
                entity.getClass().getMethod("setRegisInfDt", String.class).invoke(entity, currentDateTime);
            }
            
            // Set last change info (same as creation for new entities)
            if (hasProperty(entity, "lchgUser")) {
                entity.getClass().getMethod("setLchgUser", String.class).invoke(entity, username);
            }
            if (hasProperty(entity, "lchgInfDt")) {
                entity.getClass().getMethod("setLchgInfDt", String.class).invoke(entity, currentDateTime);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to set audit information", e);
        }
        return entity;
    }
    
    /**
     * Updates the last change information for an entity
     * @param entity The entity to update
     * @param username The username of the updater
     * @param <T> The type of entity
     * @return The entity with updated last change information
     */
    public static <T> T updateLastChangeInfo(T entity, String username) {
        String currentDateTime = DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss);
        try {
            if (hasProperty(entity, "lchgUser")) {
                entity.getClass().getMethod("setLchgUser", String.class).invoke(entity, username);
            }
            if (hasProperty(entity, "lchgInfDt")) {
                entity.getClass().getMethod("setLchgInfDt", String.class).invoke(entity, currentDateTime);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update last change information", e);
        }
        return entity;
    }
    
    /**
     * Checks if an entity has a specific property
     * @param entity The entity to check
     * @param propertyName The name of the property
     * @return True if the entity has the property, false otherwise
     */
    private static boolean hasProperty(Object entity, String propertyName) {
        try {
            String getterName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            entity.getClass().getMethod(getterName);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

}
