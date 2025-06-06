package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Getter
@Setter
@Builder
@Document(collection = "templates")
public class Template {

    @Id
    private ObjectId id;
    private String templateID;
    private String name;
    private String schemaJson;
}
