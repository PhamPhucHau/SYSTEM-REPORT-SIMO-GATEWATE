package com.org.shbvn.svbsimo.core.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;
import java.util.Collection;
import java.util.List;

public class API122WDRTemplate extends DailyCommonTemplate {
  @JsonProperty("ThoiGianDuLieu")
  private String thoigiandulieu;

  @JsonProperty("ThoiGianGuiBaoCao")
  private String thoigianguibaocao;

  @JsonProperty("TKDBTTTaiNHHT")
  private List<TkdbtttainhhtItem> tkdbtttainhht;

  public API122WDRTemplate() {
    super();
  }
@Override
  public String getThoigiandulieu() {
    return thoigiandulieu;
  }
@Override
  public void setThoigiandulieu(String thoigiandulieu) {
    this.thoigiandulieu = thoigiandulieu;
  }
@Override
  public String getThoigianguibaocao() {
    return thoigianguibaocao;
  }
@Override
  public void setThoigianguibaocao(String thoigianguibaocao) {
    this.thoigianguibaocao = thoigianguibaocao;
  }

  public List<TkdbtttainhhtItem> getTkdbtttainhht() {
    return tkdbtttainhht;
  }

  public void setTkdbtttainhht(List<TkdbtttainhhtItem> tkdbtttainhht) {
    this.tkdbtttainhht = tkdbtttainhht;
  }
  @Override // Important to indicate intent to override
public void setTkdbttTaiNHHT(List<TkdbtttainhhtItem> tkdbtttainhht) {
    this.tkdbtttainhht = tkdbtttainhht;
}
// @Override
// public Collection<TkdbtttainhhtItem> getTkdbttTaiNHHT() {
//     // TODO Auto-generated method stub
//     return tkdbtttainhht;
// }
}
