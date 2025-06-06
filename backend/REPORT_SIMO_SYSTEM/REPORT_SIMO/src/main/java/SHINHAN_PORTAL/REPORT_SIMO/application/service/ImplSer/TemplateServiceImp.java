package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.TemplateDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.Template;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.TemplateRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImp implements TemplateService {
    @Autowired
    private TemplateRepository templateRepository;
    private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImp.class);

    @Override
    public TemplateDTO create(TemplateDTO dto) {
        logger.debug("Debug log create");
        System.out.println("AAAA Before"+ dto.toString());
        Template template = Template.builder()
                .templateID(dto.getTemplateID())
                .name(dto.getName())
                .schemaJson(dto.getSchemaJson())
                .build();

        System.out.println("AAAA Convert"+ template.toString());
        return mapToDTO(templateRepository.save(template));
    }

    @Override
    public TemplateDTO update(String id, TemplateDTO dto) {
        Template template = templateRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Template không tồn tại"));

        template.setTemplateID(dto.getTemplateID());
        template.setName(dto.getName());
        template.setSchemaJson(dto.getSchemaJson());

        return mapToDTO(templateRepository.save(template));
    }

    @Override
    public void delete(String id) {
        templateRepository.deleteById(new ObjectId(id));
    }

    @Override
    public List<TemplateDTO> getAll() {
        return templateRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private TemplateDTO mapToDTO(Template template) {
        logger.info("Template @@@"+ template.toString());
        return TemplateDTO.builder()
                .id(template.getId().toHexString()) // giữ dạng String
                .templateID(template.getTemplateID())
                .name(template.getName())
                .schemaJson(template.getSchemaJson())
                .build();
    }
}
