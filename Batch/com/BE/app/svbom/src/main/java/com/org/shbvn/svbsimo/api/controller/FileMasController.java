package com.org.shbvn.svbsimo.api.controller;

import com.org.shbvn.svbsimo.repository.services.FileMasService;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.repository.entities.SimoFileMas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileMasController {

    @Autowired
    private FileMasService fileMasService;

    @GetMapping("/list")
    public List<SimoFileMas> getListUploadByDateAndTemplateId(
            @RequestParam(name = "uploadDateStart") String uploadDateStart,
            @RequestParam(name = "uploadDateEnd") String uploadDateEnd,
            @RequestParam(name = "templateCode") String templateCode,
            @RequestParam(name = "fileName", required = false) String fileName,
            @RequestParam(name = "fileStatus", required = false) String fileStatus) {
    
        return fileMasService.getListUploadByDateAndTemplateId(
                uploadDateStart,
                uploadDateEnd,
                templateCode,
                fileName,
                fileStatus
        );
    }
    
}
