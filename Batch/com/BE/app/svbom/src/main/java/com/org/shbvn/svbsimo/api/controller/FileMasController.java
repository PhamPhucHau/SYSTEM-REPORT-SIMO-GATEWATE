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
@RequestMapping("/api/file")
public class FileMasController {

    @Autowired
    private FileMasService fileMasService;

    @GetMapping("/list")
    public List<SimoFileMas> getListUploadByDateAndTemplateId(
            @RequestParam String uploadDate,
            @RequestParam String bankCode,
            @RequestParam(required = false) Long trxType) {
        Map<String, Object> params = new HashMap<>();
        params.put(APIConstant.UPLOAD_DATE_KEY, uploadDate);
        params.put(APIConstant.UPLOAD_BANKCODE_KEY, bankCode);
        params.put(APIConstant.UPLOAD_TRXTYPE_KEY, trxType);
        return fileMasService.getListUploadByDateAndTemplateId(uploadDate,bankCode);
    }
}
