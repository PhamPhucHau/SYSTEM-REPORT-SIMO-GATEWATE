package com.org.shbvn.svbsimo.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TkdbtttainhhtItem {
    @JsonProperty("MaToChucTGTT")
    private String matochuctgtt;

    @JsonProperty("SoHieuTKDBTT")
    private String sohieutkdbtt;

    @JsonProperty("SoDuTKDBTT")
    private String sodutkdbtt;

    public TkdbtttainhhtItem() {
    }

    public String getMatochuctgtt() {
      return matochuctgtt;
    }

    public void setMatochuctgtt(String matochuctgtt) {
      this.matochuctgtt = matochuctgtt;
    }

    public String getSohieutkdbtt() {
      return sohieutkdbtt;
    }

    public void setSohieutkdbtt(String sohieutkdbtt) {
      this.sohieutkdbtt = sohieutkdbtt;
    }

    public String getSodutkdbtt() {
      return sodutkdbtt;
    }

    public void setSodutkdbtt(String sodutkdbtt) {
      this.sodutkdbtt = sodutkdbtt;
    }
  }
