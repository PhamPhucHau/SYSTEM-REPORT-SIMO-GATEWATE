package SHINHAN_PORTAL.REPORT_SIMO.application.dto;


import lombok.*;

@Getter
@Setter
@Builder
@Data
public class TemplateDTO {
    private String  id;
    private String templateID;
    private String name;
    private String schemaJson;
}
