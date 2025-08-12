package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

/**
 * DTO để ghi log cho các hành động từ frontend
 */
public class FrontendActionLogDTO {
    private String actionType;
    private String resourceType;
    private String resourceId;
    private String description;
    private String pageName;
    private String componentName;
    private String additionalData;
    
    // Constructors
    public FrontendActionLogDTO() {}
    
    public FrontendActionLogDTO(String actionType, String resourceType, String resourceId, String description) {
        this.actionType = actionType;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.description = description;
    }
    
    // Getters and Setters
    public String getActionType() {
        return actionType;
    }
    
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    
    public String getResourceType() {
        return resourceType;
    }
    
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    
    public String getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPageName() {
        return pageName;
    }
    
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
    
    public String getComponentName() {
        return componentName;
    }
    
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
    
    public String getAdditionalData() {
        return additionalData;
    }
    
    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }
    
    @Override
    public String toString() {
        return "FrontendActionLogDTO{" +
                "actionType='" + actionType + '\'' +
                ", resourceType='" + resourceType + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", description='" + description + '\'' +
                ", pageName='" + pageName + '\'' +
                ", componentName='" + componentName + '\'' +
                ", additionalData='" + additionalData + '\'' +
                '}';
    }
} 