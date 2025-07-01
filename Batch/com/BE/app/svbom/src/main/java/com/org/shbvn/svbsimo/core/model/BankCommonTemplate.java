package com.org.shbvn.svbsimo.core.model;

import java.io.Serializable;

public abstract class BankCommonTemplate implements Serializable{

    private String RowNumber;

    private String Cif;

    public BankCommonTemplate() {
        super();
    }
    public BankCommonTemplate(String rowNumber, String Cif) {
        super();
        this.RowNumber = rowNumber;
        this.Cif = Cif;
    }

    public String getCif() {
        return Cif;
    }

    public void setCif(String Cif) {
        this.Cif = Cif;
    }


    public String getRowNumber() {
        return RowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.RowNumber = rowNumber;
    }
}
