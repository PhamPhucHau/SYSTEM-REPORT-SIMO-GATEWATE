package com.org.shbvn.svbsimo.core.model;

import java.io.Serializable;

public abstract class BankCommonTemplate implements Serializable{

    private String RowNumber;

    private String cif;

    public BankCommonTemplate() {
        super();
    }
    public BankCommonTemplate(String rowNumber, String cif) {
        super();
        this.RowNumber = rowNumber;
        this.cif = cif;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }


    public String getRowNumber() {
        return RowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.RowNumber = rowNumber;
    }
}
