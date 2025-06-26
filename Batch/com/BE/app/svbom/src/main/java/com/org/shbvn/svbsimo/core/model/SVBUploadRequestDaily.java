package com.org.shbvn.svbsimo.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SVBUploadRequestDaily implements Serializable{

    private static final long serialVersionUID = 1L;

    private String fileName;
    private String fileContent;
    private String fileType;
    private String fileExtension;
    private String data;

    public SVBUploadRequestDaily() {
        super();
    }

    public SVBUploadRequestDaily(String fileName, String fileContent, String fileType, String fileExtension, String data) {
        super();
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.fileType = fileType;
        this.fileExtension = fileExtension;
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getDatas() {
        return data;
    }

    public void setDatas(String data) {
        this.data = data;
    }

}
