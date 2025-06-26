package com.org.shbvn.svbsimo.core.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class DailyCommonTemplate implements Serializable{



    public DailyCommonTemplate() {
        super();
    }

    // Declare it as abstract to force subclasses to implement it
    public abstract void setTkdbttTaiNHHT(List<TkdbtttainhhtItem> list);

    public abstract Collection<TkdbtttainhhtItem> getTkdbttTaiNHHT();
    public abstract void setThoigiandulieu(String thoigiandulieu);
    public abstract String getThoigiandulieu();
    public abstract void setThoigianguibaocao(String thoigianguibaocao);
    public abstract String getThoigianguibaocao();
    
}
