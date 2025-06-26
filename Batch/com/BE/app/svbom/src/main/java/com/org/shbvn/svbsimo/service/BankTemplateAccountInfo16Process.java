package com.org.shbvn.svbsimo.service;

import java.io.File;

import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.model.BankCommonTemplate;
import com.org.shbvn.svbsimo.core.model.DailyCommonTemplate;
public interface BankTemplateAccountInfo16Process {
    
    public void process(File file) throws BaseException;
    public <T extends BankCommonTemplate> void process(File file, T obj) throws BaseException;
    public <T extends DailyCommonTemplate> void process(File file, T obj) throws BaseException;
}
