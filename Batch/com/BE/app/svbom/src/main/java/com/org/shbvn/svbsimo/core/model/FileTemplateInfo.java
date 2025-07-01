package com.org.shbvn.svbsimo.core.model;

public class FileTemplateInfo {
    
    private int sheetIndex;
    private int fromRow;
    private int headerRow;
    private int fileCnt;
    private String templateColName;
    private String templateCode;
    private String urlTemplate;
    private int autoAppr;
    private String fileType;


    public FileTemplateInfo() {
        super();
    }

    public FileTemplateInfo(int sheetIndex, int fromRow, int headerRow, int fileCnt, String templateColName, String urlTemplate, int autoAppr, String fileType) {
        super();
        this.sheetIndex = sheetIndex;
        this.fromRow = fromRow;
        this.headerRow = headerRow;
        this.fileCnt = fileCnt;
        this.templateColName = templateColName;
        this.urlTemplate = urlTemplate;
        this.autoAppr = autoAppr;
        this.fileType = fileType;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public int getFromRow() {
        return fromRow;
    }

    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    public int getHeaderRow() {
        return headerRow;
    }

    public void setHeaderRow(int headerRow) {
        this.headerRow = headerRow;
    }

    public int getFileCnt() {
        return fileCnt;
    }

    public void setFileCnt(int fileCnt) {
        this.fileCnt = fileCnt;
    }

    public String getTemplateColName() {
        return templateColName;
    }

    public void setTemplateColName(String templateColName) {
        this.templateColName = templateColName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }
        public int getAutoAppr() {
        return autoAppr;
    }

    public void setAutoAppr(int autoAppr) {
        this.autoAppr = autoAppr;
    }
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
