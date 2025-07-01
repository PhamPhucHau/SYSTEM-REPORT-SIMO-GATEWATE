package com.org.shbvn.svbsimo.repository.services;

import java.util.List;

import com.org.shbvn.svbsimo.repository.entities.SimoFileMas;

public interface FileMasService {

    public List<SimoFileMas> getListUploadByDateAndTemplateId(String dateUpload, String templateId); 
}